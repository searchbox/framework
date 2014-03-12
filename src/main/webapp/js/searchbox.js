/**
 * Handle facet actions (add/remove facet filter from url) and redirect to that url
 * @param string action Accepted value "remove" / "add" what needs to be done with the facet
 * @param string groupName The facet group name used as facet group name in filter query
 * @param string facetValue The facet value that needs to be added or removed
 * @param string baseUrl The url to redirect to, optional parameter
 **/
var handleFacet = function(action, groupName, facetValue, baseUrl) {
  //Will be done later using smart tags / q boosts
  //TODO: Fix this and make it possible to add or remove tags
  var url = $.url(window.location.href);

  var newUrl = (typeof baseUrl !== "undefined") ? baseUrl : url.attr("path");
  var paramId = 0;
  var params = $.url(window.location.href).param();
  
  if (action == "add") {
    if (typeof params["ff"] === "undefined") {
      params["ff"] = new Object();
    }

    if($.inArray(groupName + "[" + facetValue + "]", params["ff"]) == -1){
      params["ff"].push(groupName + "[" + facetValue + "]");
    }
  } else {
    delete params["ff"][groupName];
  }

  //replacing [] with '' ff=publication-type%5BCase+Reports%5D
  
  //newUrl += ($.param(params)) ? "?" + ($.param(params)) : "";
  newUrl += "?ff="+groupName + "[" + facetValue + "]";
  console.log(newUrl);
  //window.location.href = newUrl;
  //ReloadMain content doesn't work because jquery doesn't serialize the params correctly'
  //reloadMainContent(params, newUrl);
};

/** Handle events on clickable tags **/
var bindClickableTagsEvents = function(){
  $("#results .label-tag.clickable").unbind();
  $("#results .label-tag.clickable").click(function() {
    var tag = $(this);
    if($(this).hasClass("selected")){
        $('input[name=ff]').each(function(){
            if(tag.attr("data-condition-value").indexOf($(this).attr("value"))>-1){
                $(this).parent().remove();
            }
        });
    } else {
        console.log("Adding condition "+$(this).attr("data-filter"));
        var input = $("<input type='hidden'/>");
        input.attr("name", $(this).attr("data-condition"));
        input.attr("value", $(this).attr("data-condition-value"));
        $('form').append(input);
    }
    $('form').submit();
    return false;
  });
  
}

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

  // Bind all page events
  bindFacetEvents();
  bindSearchAutocompleteEvent();
  bindClickableTagsEvents();
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