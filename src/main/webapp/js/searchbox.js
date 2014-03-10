/** Handle autocomplete on searchField **/
var bindSearchAutocompleteEvent = function() {

  var cache_autocomplete = {};
  $("#edismax-query").autocomplete({
    minLength : 2,
    select : function(event, ui) {
      if (ui.item) {
        $('#edismax-query').val(ui.item.value);
        $("form").submit();
      }
    },
    source : function(request, response) {
      var term = request.term;
      if (term in cache_autocomplete) {

        var data = cache_autocomplete[term]
        response($.map(data.suggestions, function(item) {
          return {
            label : item,
            value : item
          }
        }));
        return;
      }

      $.ajax({
        url : $("#edismax-query").attr("data-autocomplete-url"),
        dataType : 'json',
        data : {
          wt : 'json',
          'json.nl' : 'arrarr',
          q : request.term,
          'terms.sort' : 'index',
          'terms.limit' : 5
        },
        jsonp : 'json.wrf',
        success : function(data) {
          // Caching response
          cache_autocomplete[term] = data;
          response($.map(data.suggestions, function(item) {
            return {
              label : item,
              value : item
            }
          }));
        }
      });
    }
  });
};

/**
 * Bind all events to facets (clicks, show more, ... The function is called each
 * time the facet list is refreshed.
 */
var bindFacetEvents = function() {

  // Show more show / show less action on the UL
  $('ul.facet-list')
      .each(
          function() {
            // Hide all facets greater than 3 and then display selected facets
            $(this).children('li:gt(3)').hide();
            $(this).children('li.selected').show();
            $(this).children('li.button').show();

            // If some facets are hidden, we add a show more button
            if ($(this).children('li:hidden').length > 0) {
              // When facets aren't refresh, we need to ensure we
              // don't add show more button one more time'
              if (!$(this).hasClass("show-more-enabled")) {
                $(this).addClass("show-more-enabled");
                $(this)
                    .append(
                        '<li class="list-group-item button"><a href="#" class="show-more">Show more...</a></li>');
              }
            }
          });

  // Click event on show more button
  $('.show-more').unbind();
  $('.show-more')
      .click(
          function(e) {
            e.preventDefault();

            if ($(this).hasClass("less")) {
              var list = $(this).parents('ul.facet-list').children('li')
                  .filter('li:gt(3)').filter("li:not(.selected)").filter(
                      "li:not(.button)");

              // $.cookie($(this).prev('ul').attr("id"), 0);
              $(this).html("Show more...");
              $(this).removeClass("less");
              list.slideUp();
            } else {

              var list = $(this).parents('ul.facet-list').children('li:hidden');
              $(this).html("Show less...");
              $(this).addClass("less");
              // isTrigger is only defined when click is simulated
              if (typeof (e.isTrigger) === 'undefined') {
                list.slideDown();
              } else {
                list.show(); // Display the list faster
              }

              // $.cookie($(this).prev('ul').attr("id"), 1);
            }
          });

  $('ul.facet-list').each(function() {
    // Triggers click if required
    /*
     * if ($.cookie($(this).attr('id')) == 1) {
     * $(this).next("a.show-more").click(); }
     */
  });
};

var bindResultPageEvents = function() {

  // Bind facet events
  bindFacetEvents();
  bindSearchAutocompleteEvent();
};

$(document).ready(function() {
  // Cleans all params that are not set.
  $('form#searchForm').submit(function() {
    $(':input', this).each(function() {
      this.disabled = !($(this).val());
    });
  });

  bindResultPageEvents();

  // Enable AJAX tabs in Bootstrap
  $('[data-toggle="tabajax"]').click(function(e) {
    e.preventDefault()
    var loadurl = $(this).attr('href')
    var targ = $(this).attr('data-target')
    $.get(loadurl, function(data) {
      $(targ).html(data)

    });
    $(this).tab('show')
  });
});