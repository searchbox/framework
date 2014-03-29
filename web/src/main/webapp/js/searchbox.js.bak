function urlencode(str) {
  return escape(str.replace(/%/g, '%25').replace(/\+/g, '%2B')).replace(/%25/g,
      '%');
}

function filterFormSubmit() {

  var $form = $("#filterForm");
  // disable empty fields so they don't clutter up the url
  $form.find(':input[value=""]').attr('disabled', true);

  jQuery('#main-search-content').hideLoading();
  jQuery('#main-search-content').showLoading();

  var url = $form.attr("action");

  // updatePageUrl(params, url);

  var data = $form.find(":input[value]").serialize();
  $("#tag-bar .btn").each(function() {
    var parr = $(this).attr("data-filter").split(":");
    data += "&" + ("ff[" + parr[0] + "]=" + parr[1]);
  });

  window.history.pushState("", "Title", url + (data) ? "?" + data : "");

  // Fake the data with some dummy parameter to workaround the bug
  // https://code.google.com/p/chromium/issues/detail?id=108425
  data += (data) ? "&t=js" : "t=js"

  $.ajax({
    type : 'GET',
    data : data,
    url : url,
    success : function(data, textStatus) {
      jQuery('#main-search-content').html(data);
      jQuery('#main-search-content').hideLoading();
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      jQuery('#main-search-content').hideLoading();
    }
  });
  bindResultPageEvents();

  return false;
}

/**
 * Updating the browser URL with new parameters (ajax history)
 */
var updatePageUrl = function(params, baseUrl) {
  var url = $.url(window.location.href);

  var newUrl = (typeof baseUrl !== "undefined") ? baseUrl : url.attr("path");
  var paramId = 0;

  newUrl += ($.param(params)) ? "?" + ($.param(params)) : "";
  window.history.pushState("", "Title", newUrl);
};

var sortComboClicked = function(field, order) {
  var params = $.url(window.location.href).param();
  params["s.f"] = field ? field : undefined;
  params["s.o"] = order ? order : undefined;

  // Deleting entries from param array if undefined
  if (typeof params["s.o"] === "undefined") {
    delete params["s.o"]
  }

  if (typeof params["s.f"] === "undefined") {
    delete params["s.f"]
  }

  reloadMainContent(params, "");
  return false;
}
/**
 * Function to reload main content in ajax
 */
var reloadMainContent = function(params, url) {
  jQuery('#main-search-content').hideLoading();
  jQuery('#main-search-content').showLoading();

  // Need to figure out the current path if not passed as a parameter
  url = (url) ? url : $.url("/" + window.location.href).segment().join("/");

  updatePageUrl(params, url);

  $.ajax({
    type : 'GET',
    data : params,
    url : url,
    success : function(data, textStatus) {
      jQuery('#main-search-content').html(data);
      jQuery('#main-search-content').hideLoading();
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      jQuery('#main-search-content').hideLoading();
    }
  });
  bindResultPageEvents();
};

var handleRangeFacet = function(groupName, facetValue, baseUrl) {
  // TODO: Try to use different handles for left and right:
  // http://jquery.10927.n7.nabble.com/Two-different-range-slider-handles-td137990.html
  var redirectTo = (typeof baseUrl === "undefined") ? "/search" : baseUrl;

  var params = $.url(window.location.href).param();
  params["fq." + groupName] = facetValue;

  reloadMainContent(params, redirectTo);
};

var loadRelatedContent = function(selector, baseUrl, queryString) {

  // var params = $.url(window.location.href).param();
  if (jQuery('#' + selector).length) {
    $.ajax({
      type : 'GET',
      data : queryString,
      url : baseUrl,
      dataType : "jsonp",
      success : function(data, textStatus) {
        jQuery('#' + selector).html(data.html);
      },
      error : function(XMLHttpRequest, textStatus, errorThrown) {
        if (textStatus) {
          jQuery('#' + selector).html("No related content");
        }
      }
    });
  }
};

var discoverRelatedContent = function(selector, baseUrl, queryString) {

  var quotes = [ "--",
      "As we speak, searchbox is \"reading\" your article's content",
      "literally reading it to understand what it is about",
      "and then searches - mines - for documents",
      "which talk about the same concepts...",
      "please be patient, this is a complex task", "10...", "9...", "8...",
      "7...", "6...", "5...", "4...", "3...", "2...", "1...", "0...",
      "Almost done!" ]; // 18 entries, timeout at 40 secs

  $("#discover-button-" + selector).hide();
  $("#discover-progress-" + selector).show();

  var ecount = 0;
  $(".entertain").hide().html("Content Discovery is a crazy tool").fadeIn();

  var entertain = setInterval(function() {
    $(".entertain").fadeOut(function() {
      $(this).html(quotes[ecount]).fadeIn();
    });
    ecount = ecount + 1;
  }, 2200);

  var progress = setInterval(function() {
    var $bar = $('.bar');

    if ($bar.width() == 400) {
      clearInterval(progress);
      $('.progress').removeClass('active');
    } else {
      $bar.width($bar.width() + 4);
    }
  }, 200);

  // var params = $.url(window.location.href).param();
  if (jQuery('#' + selector).length) {
    $.ajax({
      type : 'GET',
      data : queryString,
      timeout : 40000,
      url : baseUrl,
      dataType : "jsonp",
      success : function(data, textStatus) {
        jQuery('#' + selector).slideUp(function() {
          $(this).html(data.html).slideDown();
        });
      },
      error : function(XMLHttpRequest, textStatus, errorThrown) {
        if (textStatus) {
          jQuery('#' + selector).html(
              "Sorry, we couldn't find any related content");
        }
      }
    });
  }
  ;
};

/**
 * Method used to truncate text, uses string and limit as input
 */
var truncate = function(string, limit) {
  var dots = "...";
  if (string.length > limit) {
    string = string.substring(0, limit) + dots;
  }
  return string;
}

/**
 * Handle facet actions (add/remove facet filter from url) and redirect to that
 * url
 * 
 * @param string
 *          action Accepted value "remove" / "add" what needs to be done with
 *          the facet
 * @param string
 *          groupName The facet group name used as facet group name in filter
 *          query
 * @param string
 *          facetValue The facet value that needs to be added or removed
 * @param string
 *          baseUrl The url to redirect to, optional parameter
 */
var handleFacet = function(action, groupName, facetValue, baseUrl) {
  // Will be done later using smart tags / q boosts

  var url = $.url(window.location.href);

  var newUrl = (typeof baseUrl !== "undefined") ? baseUrl : url.attr("path");
  var paramId = 0;
  var params = $.url(window.location.href).param();

  if (action == "add") {
    if (typeof params["ff"] === "undefined") {
      params["ff"] = new Object();
    }

    params["ff"][groupName] = facetValue;
  } else {
    delete params["ff"][groupName];
  }

  // replacing [] with ''
  newUrl += ($.param(params)) ? "?"
      + ($.param(params)).split('%5B%5D').join('') : "";
  window.location.href = newUrl;
  // ReloadMain content doesn't work because jquery doesn't serialize the
  // params correctly'
  // reloadMainContent(params, newUrl);
};

/**
 * Bind all events to facets (clicks, show more, ... The function is called each
 * time the facet list is refreshed.
 */
var bindFacetEvents = function() {

  /**
   * Handle clicks on result attributes
   */
  $("#main-col-center .field.clickable").unbind();
  $("#main-col-center .field.clickable").click(function() {
    var filter = $(this).attr("data-filter").split(":");
    if (filter.length == 2) {
      var path = $.url(window.location.href).attr("path");
      var pieces = path.split("/");

      var baseUrl = path;
      if (pieces[2] != "search") {
        baseUrl = "/" + pieces[1] + "/search"
      }

      handleFacet("add", filter[0], filter[1], baseUrl);
    }
    return false;
  });

  /**
   * 
   */
  $("#tag-bar .top-tag").unbind();
  $("#tag-bar .top-tag").click(function() {
    var filter = $(this).attr("data-filter").split(":");
    if (filter.length == 2) {
      var path = $.url(window.location.href).attr("path");
      var pieces = path.split("/");

      var baseUrl = path;
      if (pieces[2] != "search") {
        baseUrl = "/" + pieces[1] + "/search"
      }
      handleFacet("remove", filter[0], filter[1], baseUrl);
    }
    return false;
  });

  /**
   * Handle clicks on facet checkboxes
   */
  $(".facet-list input[type=\"checkbox\"]").unbind();
  $(".facet-list input[type=\"checkbox\"]").click(function() {
    filterFormSubmit();
  });

  // Show more show less action on the UL
  $('ul.facet-list').each(function() {
    // Hide all facets greather than 4 and then display
    // selected facets
    $(this).children('li:gt(4)').hide();
    $(this).children('li.selected').show();

    // TODO: Count based on invisible items rather than all
    // li.
    if ($(this).children('li:hidden').length > 0) {
      // When facets aren't refresh, we need to ensure we
      // don't add show more button one more time'
      if (!$(this).hasClass("show-more-enabled")) {
        $(this).addClass("show-more-enabled");
        $(this).after('<a href="#" class="show-more">Show More...</a>');
      }
    }
  });

  // Click event on show more button
  $('.show-more').unbind();
  $('.show-more').click(
      function(e) {
        e.preventDefault();

        if ($(this).hasClass("less")) {
          var list = $(this).prev('ul.nav-list').children('li').filter(
              'li:gt(4)').filter("li:not(.selected)");

          $.cookie($(this).prev('ul').attr("id"), 0);
          $(this).html("Show More...");
          $(this).removeClass("less");
          list.slideUp();
        } else {

          var list = $(this).prev('ul.nav-list').children('li:hidden');

          $(this).html("Show less...");
          $(this).addClass("less");
          // isTrigger is only defined when click is simulated
          if (typeof (e.isTrigger) === 'undefined') {
            list.slideDown();
          } else {
            list.show(); // Display the list faster
          }

          $.cookie($(this).prev('ul').attr("id"), 1);
        }
      });

  $('ul.facet-list').each(function() {
    // Triggers click if required
    if ($.cookie($(this).attr('id')) == 1) {
      $(this).next("a.show-more").click();
    }
  });

  // Do noting when someone hit enter on the autocomplete box
  $(".sidebar-nav .autocomplete-box").unbind();
  $(".sidebar-nav .autocomplete-box").keypress(function(event) {
    // Return key
    if (event.which == 13) {
      return false;
    }
  });

  /**
   * Handle click on distcombo (geoloc)
   */
  $(".dist-combo li a").unbind();
  $(".dist-combo li a").click(function() {
    $("#dist").val($(this).attr("data-dist"));
    filterFormSubmit();
  });

  $("#city").unbind();
  $("#city").autocomplete(
      {
        source : function(request, response) {
          var biais = $("#city").attr("data-countryBiais");
          $.ajax({
            url : "http://ws.geonames.org/searchJSON",
            dataType : "jsonp",
            data : {
              username : "searchbox",
              featureClass : "P",
              style : "full",
              countryBias : biais,
              maxRows : 12,
              name_startsWith : request.term
            },
            success : function(data) {
              response($.map(data.geonames, function(item) {
                return {
                  label : item.name
                      + (item.adminName1 ? ", " + item.adminName1 : "") + ", "
                      + item.countryName,
                  value : item.name,
                  lat : item.lat,
                  lng : item.lng
                }
              }));
            }
          });
        },
        minLength : 2,
        select : function(event, ui) {
          $("#city").attr("value", ui.item.value)
          $("#city").val(ui.item.value)
          $("#lat").attr("value", ui.item.lat)
          $("#lng").attr("value", ui.item.lng)
          filterFormSubmit();
        },
        open : function() {
          $(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close : function() {
          $(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
      });

  $(".sidebar-nav .autocomplete-box").autocomplete({
    source : function(request, response) {
      $.ajax({
        url : $(this.element).get(0).getAttribute("serviceurl"),
        data : {
          'q' : request.term
        },
        success : function(data) {

          if (data.results.length == 0) {
            $(this).autocomplete("close");
          }
          response($.map(data.results, function(item) {
            return {
              label : truncate(item, 30),
              value : item
            };
          }));
        }
      })
    },
    select : function(event, ui) {
      // TODO: find a way to pass base url
      handleFacet("add", $(this).get(0).getAttribute("field"), ui.item.value);
    }
  });
};

var bindResultPageEvents = function() {

  // Bind facet events
  bindFacetEvents();

  // Bind popover events
  jQuery
      .each(
          $('.popover-data'),
          function(key, value) {
            var idElem = $(value).attr("data-trigger");
            $('#' + idElem)
                .popover(
                    {
                      title : $(value).attr("data-title"),
                      content : $(value).html(),
                      trigger : "manual",
                      template : '<div class="popover" onmouseover="clearTimeout(timeoutObj);$(this).mouseleave(function() {$(this).hide();});"><div class="arrow"></div><div class="popover-inner"><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>',
                      placement : "bottom"
                    }).click(function(e) {
                  // e.preventDefault() ;
                }).mouseenter(function(e) {
                  $(this).popover('show');
                }).mouseleave(function(e) {
                  var ref = $(this);
                  timeoutObj = setTimeout(function() {
                    ref.popover('hide');
                  }, 50);
                });
          });
};

$(document).ready(
    function() {

      $("#searchField").clearSearch();

      if ($("#searchField").length > 0) {
        $("#searchField").focus();
        // Setting the focus AT THE END of the field
        $("#searchField").val($("#searchField").val());
      }

      $("#searchField-sense").keydown(
          function(event) {

            if ($(this).val().split(" ").length <= 5) {
              if ($("#searchField-sense").is(":visible")) {
                $("#searchField-sense").hide();
                $("#searchField-sense").attr("name", "");
                $("#searchField").attr("name", "q");
                $("#searchField").parent('.clear_input_div').show();
                $("#searchField").val('');
                $("#searchField").focus();
                $("#searchField").val($("#searchField-sense").val());
              }
            }

            $('#searchField').val(
                $(this).val() + String.fromCharCode(event.keyCode));
          });

      $("#searchField-sense").autogrow();
      /**
       * Handle autocomplete on the search field
       */
      $('#searchField').autocomplete(
          {
            select : function(event, ui) {

              if ($('#searchField').hasClass("search")) {
                var params = $.url(window.location.href).param();
                params["q"] = ui.item.value;
                reloadMainContent(params, '');
              } else {
                if (ui.item) {
                  $('#searchField').val(ui.item.value);
                  $('#searchField').closest(".form-search").submit();
                }
              }
            },
            source : function(request, response) {
              if (request.term.split(" ").length <= 5) {
                $('#searchField').attr("autocomplete", "off");

                $.ajax({
                  url : '/ajax/query',
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
                    response($.map(data.suggestions, function(item) {
                      return {
                        label : item,
                        value : item
                      }
                    }));
                    var cq = $('#searchField').val();
                    if (cq.length >= 2) {
                      /*
                       * if(data.collationQuery!= null &&
                       * data.collationQuery.length > 2) { Ê Ê Ê Ê Ê Ê Ê Ê Ê Ê Ê
                       * cq = data.collationQuery; Ê Ê Ê Ê Ê Ê Ê Ê Ê Ê }
                       */
                      if (data.suggestions.length > 0) {
                        cq = data.suggestions[0];
                      }

                      // Only run
                      // the live
                      // search if
                      // on the
                      // result
                      // page or
                      // livesearch
                      // is active
                      if ($('#searchField').hasClass("search")) {
                        // Injecting
                        // url
                        // params
                        // to
                        // AJAX
                        // request
                        // to
                        // keep
                        // the
                        // facet
                        // selection
                        var params = $.url(window.location.href).param();
                        params["q"] = cq;

                        // Removing
                        // filter
                        // queries
                        $.each(params, function(key, val) {
                          if (key.length >= 3 && key.substr(0, 3) == "fq.") {
                            params[key] = undefined;
                          }

                          if (key == "offset" || key == "max") {
                            params[key] = undefined;
                          }
                        });
                        if (data.suggestions.length > 0) {
                          reloadMainContent(params, '');
                        }
                      }
                    }
                  }
                });
              } else {

                if ($("#searchField").is(":visible")
                    && $("#searchField-sense").length > 0) {
                  $("#searchField").parent('.clear_input_div').hide();
                  $("#searchField").attr("name", "");
                  $("#searchField-sense").show();
                  $("#searchField-sense").attr("name", "q");
                  $("#searchField-sense").val('');
                  $("#searchField-sense").focus();
                  $("#searchField-sense").val($("#searchField").val());
                }
              }
            }
          });

      /**
       * Loop over presets and add them to the preset bar
       */
      var updatePresets = function() {
        jQuery.each($(".preset_container div.preset a"), function(key, value) {
          var li = "<li>";
          if ($(value).parent(".preset").hasClass("active")) {
            var li = '<li class="active">';
          }
          $("#preset-bar").append(
              li + '<a href="' + $(value).attr("href") + '">' + $(value).html()
                  + '</a></li>')
        });

        $(".preset_container").hide();
      };
      updatePresets();

      /**
       * Bind slider "slid" event to the buttons
       */
      $("#relatedCarousel").bind(
          "slid",
          function() {
            $("#relatedCarousel div.item").each(
                function(index) {

                  if ($(this).hasClass("active")) {
                    $("#relatedCarousel .slidetabs a").each(
                        function(index2) {
                          (index == index2) ? $(this).attr("class", "current")
                              : $(this).attr("class", "");
                        });
                  }
                });
          });

      /**
       * Handle click event on buttons
       */
      $(".slidetabs a").click(function() {
        $current = $(this);
        $("#relatedCarousel .slidetabs a").each(function(index2) {
          if ($(this).attr("id") == $current.attr("id")) {
            $('.carousel').carousel(index2);
          }
        });
        return false;
      });

      // When clicking on the login form in the search bar, stops
      // event propagation
      $('.dropdown-menu').find('form').click(function(e) {
        e.stopPropagation();
      });

      bindResultPageEvents();
    });