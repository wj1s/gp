            function checkLodop(webRootPath){
            	   var oldVersion=LODOP.Version;
            	       newVerion="5.0.0.3";	
            	       var msg="";
            	   if (oldVersion==null){
            		   msg="<h3><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+webRootPath+"/plugin/print/install_lodop.exe'>执行安装</a>,安装后请刷新页面。</font></h3>";
	            		if (navigator.appName=="Netscape"){
	            			msg="<h3><font color='#FF00FF'>（Firefox浏览器用户需先点击这里<a href='npActiveXFirefox4x.xpi'>安装运行环境</a>）</font></h3>";
	            		}
	            		document.all.printMsg.innerHTML=msg;
	            		return false;
            	   } else if (oldVersion<newVerion){
            		   msg="<h3><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+webRootPath+"/plugin/print/install_lodop.exe'>执行升级</a>,升级后请重新进入。</font></h3>";
            		   document.all.printMsg.innerHTML=msg;
            		   return false;
            	   } 
            	   return true;
           } 