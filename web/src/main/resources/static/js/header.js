/**
 * Created by lenovo on 2016/11/18.
 */
$(function(){
    $('a.target').attr('target','_blank');
    //首页左侧分类菜单
    $(".category ul.menu").find("li").each(
        function() {
            $(this).hover(
                function() {
                    var cat_id = $(this).attr("cat_id");
                    var menu = $(this).find("div[cat_menu_id='"+cat_id+"']");
                    menu.show();
                    $(this).addClass("hover");
                    var menu_height = menu.height();
                    if (menu_height < 60) menu.height(80);
                    menu_height = menu.height();
                    var li_top = $(this).position().top;
                    $(menu).css("top",-li_top + 38);
                },
                function() {
                    $(this).removeClass("hover");
                    var cat_id = $(this).attr("cat_id");
                    $(this).find("div[cat_menu_id='"+cat_id+"']").hide();
                }
            );
        }
    );
    $(".head-user-menu dl").hover(function() {
            $(this).addClass("hover");
        },
        function() {
            $(this).removeClass("hover");
        });
    $('.head-user-menu .my-cart').mouseover(function(){// 运行加载购物车
        $(this).unbind('mouseover');
    });
    $('#button').click(function(){
        if ($('#keyword').val() == '') {
            return false;
        }
    });
    $('#keyword').focus(function(){
        if ($(this).val() == $(this).attr('title')) {
            $(this).val('').removeClass('tips');
        }
    }).blur(function(){
        if ($(this).val() == '' || $(this).val() == $(this).attr('title')) {
            $(this).addClass('tips').val($(this).attr('title'));
        }
    }).blur().autocomplete({
        source: function (request, response) {
            $.getJSON('http://www.huashi520.com/index.php?act=search&op=auto_complete', request, function (data, status, xhr) {
                $('#top_search_box > ul').unwrap();
                response(data);
                if (status == 'success') {
                    $('body > ul:last').wrap("<div id='top_search_box'></div>").css({'zIndex':'1000','width':'362px'});
                }
            });
        },
        select: function(ev,ui) {
            $('#keyword').val(ui.item.label);
            $('#top_search_form').submit();
        }
    });
    var $search_url = $("#search").children('ul');
    $search_url.children('li').click(function(){
        $(this).parent().children('li').removeClass("current");
        $(this).addClass("current");
        $('#search_act').attr("value",$(this).attr("act"));
        $('#keyword').attr("placeholder",$(this).attr("title"));
    });
    $("#keyword").blur();

});
