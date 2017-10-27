<!DOCTYPE HTML>
<%-- @elvariable id="command" type="org.soul.model.sys.vo.SysParamListVo" --%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<form:form action="${root}/setting/preference/savePreference.html" method="post">
    <%--<div id="validateRule" style="display: none">${command.validateRule}</div>--%>
      <div class="row">
          <div class="position-wrap clearfix">
              <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
              <span>${views.sysResource['系统设置']}</span><span>/</span><span>${views.sysResource['基础设置']}</span>
          </div>
          <div class="col-lg-12">
              <div class="wrapper white-bg shadow">
                  <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
                      <ul class="clearfix sys_tab_wrap">
                          <li><a href="/param/index.html?random=${random}" nav-target="mainFrame">${views.setting['setting.parameter.preference']}</a></li>
                          <li><a href="/betLimit/setting.html?random=${random}" nav-target="mainFrame">${views.setting['setting.parameter.game']}</a></li>
                          <li><a href="/param/verification.html?random=${random}" nav-target="mainFrame">${views.setting['setting.parameter.gameMaintain']}</a></li>
                      </ul>
                      <div class="clearfix filter-wraper border-b-1">
                          <soul:button target="resetPreference" text="${views.setting['page.preference.default']}" opType="function" confirm="${views.setting['page.preference.confirm']}" callback="reload" cssClass="btn btn-filter">${views.setting['page.preference.default']}</soul:button>
                      </div>
                      <div class="clearfix line-hi34 bg-gray p-sm">
                          <label class="ft-bold pull-left m-r">${views.setting['preference.privilage']}：</label>
                          <div class="col-xs-10 p-x">
                              <div class="input-group date bg-white pull-left m-r">
                                  <select class="btn-group chosen-select-no-single" name="sysParam.paramValue">
                                      <option value="">${views.common['pleaseSelect']}</option>
                                      <c:forEach items="${privilagePassMap}" var="item">
                                          <option value="${item.key}" ${item.key eq privilagePassTime.paramValue?'selected="selected"':''}>${dicts[item.value.module][item.value.dictType][item.value.dictCode]}</option>
                                      </c:forEach>
                                  </select>
                                  <input type="hidden" name="sysParam.id" value="${privilagePassTime.id}" >
                              </div>${views.setting['preference.securityPwdSettingTips']}
                          </div>
                      </div>
                      <div class="clearfix">

                        <div class="col-lg-12 site-switch">
                            <h3>${views.setting['page.preference.remindmusic']}</h3>
                            <ul class="content clearfix">
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover dataTable m-b-sm m-t">
                                        <thead>
                                            <tr class="bg-gray">
                                              <th>${views.setting['page.preference.remindmusicproject']}</th>
                                              <th>${views.setting['page.preference.currentuse']}</th>
                                              <th>${views.setting['page.preference.enabletone']}</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${warmToneMap}" var="item">
                                                <tr>
                                                    <td>${dicts[item.value.module][item.value.dictType][item.value.dictCode]}</td>
                                                    <c:choose>
                                                        <c:when test="${item.value['dictCode'] eq toneWarm.paramCode}">
                                                            <c:set var="tonePath" value="${!(empty toneWarm.paramValue)?toneWarm.paramValue:toneWarm.defaultValue}"/>
                                                            <td>
                                                                <div class="pull-left">
                                                                    <audio src="${root}/static/${tonePath}" preload="auto"></audio>
                                                                </div>
                                                                <soul:button target="${root}/param/editTone.html?paramCode=${toneWarm.paramCode}" cssClass="m-l-sm line-hi30" text="修改${dicts[item.value.module][item.value.dictType][item.value.dictCode]}" tag="a" opType="dialog" callback="reload">${views.setting['page.preference.replace']}</soul:button>
                                                            </td>
                                                            <td><input type="checkbox" name="active" hidId="toneWarm" value="${toneWarm.active?'true':'false'}" data-size="mini" ${toneWarm.active?'checked':''}></td>
                                                            <input type="hidden" name="toneSysParamList[0].id" value="${toneWarm.id}">
                                                            <input type="hidden" name="toneSysParamList[0].active" id="toneWarm" value="${toneWarm.active?'true':'false'}">
                                                        </c:when>

                                                        <c:when test="${item.value['dictCode'] eq toneNotice.paramCode}">
                                                            <c:set var="tonePath" value="${!(empty toneNotice.paramValue)?toneNotice.paramValue:toneNotice.defaultValue}"/>
                                                            <td>
                                                                <div class="pull-left">
                                                                    <audio src="${root}/static/${tonePath}" preload="auto"></audio>
                                                                </div>
                                                                <soul:button target="${root}/param/editTone.html?paramCode=${toneNotice.paramCode}" cssClass="m-l-sm line-hi30" text="修改${dicts[item.value.module][item.value.dictType][item.value.dictCode]}" tag="a" opType="dialog" callback="reload">${views.setting['page.preference.replace']}</soul:button>
                                                            </td>
                                                            <td><input type="checkbox" name="active" hidId="toneNotice" value="${toneNotice.active?'true':'false'}" data-size="mini" ${toneNotice.active?'checked':''}></td>
                                                            <input type="hidden" name="toneSysParamList[1].id" value="${toneNotice.id}">
                                                            <input type="hidden" name="toneSysParamList[1].active" id="toneNotice" value="${toneNotice.active?'true':'false'}">
                                                        </c:when>
                                                    </c:choose>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </ul>
                        </div>
                      </div>
                  </div>
                  <div class="operate-btn">
                      <soul:button text="${views.common['save']}" dataType="json" opType="ajax" refresh="true" target="${root}/param/savePreference.html" post="getCurrentFormData" callback="reload" cssClass="btn btn-filter btn-lg m-r"></soul:button>
                  </div>
              </div>
          </div>
      </div>
  </div>
</form:form>
</html>
<script src="${resComRoot}/js/audiojs/audio.min.js"></script>
<script>
    audiojs.events.ready(function() {
        audiojs.createAll();
    });
</script>
<soul:import res="site/setting/PlatformParam/preferences/PreferenceEdit"/>