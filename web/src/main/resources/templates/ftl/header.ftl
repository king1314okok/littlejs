<div class="public-top-layout w">
    <div class="topbar wrapper">
        <div class="quick-menu">
            <#if (user)??>
            <dl style="width: 150px">
                <dt>
                        <label>欢迎您,&nbsp;${user.username}&nbsp;</label>&nbsp;&nbsp;
                        <a id="logoutBtn" name="logoutBtn" href="/logout" rel="nofollow">登出</a>
                </dt>
            </dl>
            <#else>
            <dl>
                <dt>
                请<a href="/login" rel="nofollow">登录</a>/
                <a href="/register" rel="nofollow">注册</a>
                </dt>
            </dl>
            </#if>
            <dl><dt><a href="javascript:window.open('/user');" rel="nofollow">我的订单</a><i></i></dt></dl>
            <dl><dt><a href="javascript:window.open('/favorite');" rel="nofollow">商品收藏</a></dt></dl>
            <dl><dt><a href="javascript:window.open('tencent://message/?uin=759046694&Site=你好&Menu=yes');" rel="nofollow" target="_blank"><label style="color:red;color: blue;cursor: pointer;">合作或加盟</label></a></dt></dl>
        </div>
    </div>
</div>

<!-- 大搜索框 -->
<div class="header-wrap">
    <header class="public-head-layout wrapper">
        <div class="site-logo"><a href="/"><img src="${ctx}/static/images/logo.jpg" class="pngFix"></a></div>
        <h1 class="keyss">花市鲜花网</h1>
        <div id="search" class="head-search-bar">
            <ul class="tab"><li title="请输入您要搜索的商品关键字" act="search" class="current">商品</li></ul>
            <form class="search-form" method="get" action="/flower/flowerList">
                <input placeholder="请输入您要搜索的花名" id="keyword" name="keyword" type="text" class="input-text" value="" maxlength="60"/>
                <input type="submit" id="button" value="搜索" class="input-submit">
            </form>
            <div class="keyword">热门搜索：
                <ul>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=玫瑰">玫瑰</a></li>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=百合">百合</a></li>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=蓝色妖姬">蓝色妖姬</a></li>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=爱情">爱情</a></li>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=花篮">花篮</a></li>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=老师">老师</a></li>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=七夕节">七夕节</a></li>
                    <li><a rel="nofollow" href="/flower/flowerList?keyword=情人节">情人节</a></li>
                </ul>
            </div>
        </div>
        <div class="head-user-menu">
            <dl class="my-mall"> <dt class="kf_dh"> <img src="${ctx}/static/images/tel.png" />4000-XXX-XXX</dt> </dl>
            <dl class="my-mall">
                <dt class="kf_53s"><a href="javascript:window.open('tencent://message/?uin=759046694&Site=你好&Menu=yes');" style="color:red;">
                        <img src="${ctx}/static/images/kefu.gif" alt="在线客服" />&nbsp;在线客服</a></dt>
            </dl>
            <dl class="my-cart">
                <dt><a href="javascript:window.open('/cart');" class="ico" style="text-align: center">购物车结算</a></dt>
            </dl>
        </div>
    </header>
</div>