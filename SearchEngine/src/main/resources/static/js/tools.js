// 获取当前url中某个参数的方法
function getURLParam(key) {
    var params=location.search;
    if(params.indexOf("?")>=0) {//如果存在查询字符“？”
        params=params.substring(1);
        let paramArr=params.split('&');
        for(let i=0;i<paramArr.length;i++) {
            let namevalues=paramArr[i].split("=");
            if(namevalues[0]==key) {
                return namevalues[1];
            }
        }
    }else {
        return "";
    }
}
/*
用于获取 URL 参数中指定 key 的值。
首先，使用 location.search 获取 URL 中的参数部分。
如果 URL 中存在参数（即 location.search 不为空，且包含 "?"），
则使用 substring 和 split 方法将参数转换为一个数组。循环遍历数组，
并使用 split 方法将每个参数拆分为 key 和 value，如果 key 与函数参数 key 相等，
则返回该 value。如果没有找到匹配的 key，则返回空字符串。
*/

// 用于退出登录
function onExit() {
    if(confirm("确认退出？")) {
        //ajax请求后端进行退出操作
        jQuery.ajax({
            url:"/user/logout",
            type:"POST",
            data:{},
            success:function(result) {
                location.href="/login.html";
            },
            error:function(err) {
                if(err!=null&& err.status==401) {
                    alert("用户未登录，即将跳转到登录页!");
                    // 已经被拦截器拦截了，未登录
                    location.href="/login.html"
                }
            }
        });
    }
}