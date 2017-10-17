<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String serverName = request.getServerName();
String svnBasePath = "svn://" + serverName + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>创建项目 - ${applicationScope.sysName}</title>
    <jsp:include page="../common/init_style.jsp"/>
    <style>
    .input-group-addon {
	    padding: 6px 12px;
	    font-size: 14px;
	    font-weight: 400;
	    line-height: 1;
	    color: #555;
	    text-align: center;
	    background-color: #eee;
	    border: 1px solid #ccc;
	    border-radius: 4px;
    }
    </style>
</head>
<body class="gray-bg top-navigation">
<div id="wrapper">
    <div id="page-wrapper" class="gray-bg">
        <jsp:include page="../common/header.jsp"/>
        <div class="wrapper wrapper-content">
            <div class="container">
                <div class="row list-margin">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>创建项目</h5>
                        </div>
                        <div class="ibox-content">
                            <!-- 查询表单 -->
                            <form class="form-horizontal m-t" id="submitForm" action="#" onsubmit="return submitForm();" method="post">
                                <div class="col-md-12 clear-float">
									<input type="hidden" id="serverName" value="<%=serverName %>">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">项目名称：</label>
                                        <div class="col-sm-8">
                                            <input type="text" placeholder="请输入项目名称" name="pj" id="projectName" class="form-control" required >
                                            <span class="help-block m-b-none"><i class="fa fa-info-circle">&nbsp;</i>请填写项目名称，用作资源目录名，支持中文</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">项目类型：</label>
                                        <div class="col-sm-8">
                                            <select name="type" class="form-control">
                                                <option value="svn">svn</option>
                                                <%--<option value="http">http</option>--%>
                                                <%--<option value="http-mutil">http(多库)</option>--%>
                                            </select>
                                        </div>
                                    </div>

									<input type="hidden" name="path" id="projectSvnPath">
									<input type="hidden" name="svnRepoPath" id="svnRepoPath" value="${SVN_BASE_PATH }">

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">项目 SVN地址 ：</label>
                                        <div class="col-sm-8">
                                            <input type="hidden" name="url" id="projectSvnUrl">
                                             <div class="input-group">
										      <div class="input-group-addon"><%=svnBasePath %></div>
										      <input type="text" class="form-control" id="projectSvnName" placeholder="project name" oninput="inputName(this.value)" onpropertychange="inputName(this.value)">
										    </div>
                                            <input type="hidden" name="svnurl" class="form-control" id="projectSvnUrlShow" readonly>
                                            <span class="help-block m-b-none"><i class="fa fa-info-circle">&nbsp;</i>请输入项目SVN名称，支持英文、数字以及下划线</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">项目描述：</label>
                                        <div class="col-sm-8">
                                            <input type="text" placeholder="请输入项目描述" name="des" class="form-control" required>
                                            <span class="help-block m-b-none"><i class="fa fa-info-circle">&nbsp;</i>SVN项目的简单描述信息</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label"></label>
                                        <div class="col-sm-4 col-sm-offset-2">
                                            <button class="btn btn-primary" type="submit">创建</button>
                                            <button class="btn btn-white" type="reset">重置</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/init_script.jsp"/>
<script type="text/javascript">
    seajs.use("${appPath}/script/modules/svn/pj_create");
</script>
</body>
</html>