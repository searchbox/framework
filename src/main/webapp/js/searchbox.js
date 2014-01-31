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

};

$(document).ready(function() {
  bindResultPageEvents();
});