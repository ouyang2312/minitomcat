package com.ouyang.builder;

import com.ouyang.config.ServerConfig;
import com.ouyang.exception.BadRequestException;
import com.ouyang.exception.PageNotFoundException;
import com.ouyang.exception.ResponseNotInitException;
import com.ouyang.http.HttpServletRequest;
import com.ouyang.http.HttpServletResponse;
import com.ouyang.utils.GZIPUtils;
import com.ouyang.utils.StringUtil;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author oy
 * @description
 * @date 2019/12/17
 */
public abstract class HttpBuilder {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected static final String splitFlag = "\r\n\r\n";

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void buildResponse() throws IOException {
        if (response == null) {
            response = new HttpServletResponse();
        }
        buildResponse(response.getHttpCode(), response.getOutputStream().toByteArray());
    }

    public void buildResponse(byte[] data) throws IOException {
        if (response == null) {
            response = new HttpServletResponse();
        }
        buildResponse(response.getHttpCode(), data);
    }

    public void buildResponse(int httpCode, String msg) throws IOException {
        if (response == null) {
            response = new HttpServletResponse();
        }
        buildResponse(httpCode, msg.getBytes(ServerConfig.ENCODE));
    }

    public void buildResponse(int httpCode, byte[] data) throws IOException {
        if (response == null) {
            response = new HttpServletResponse();
        }
        buildResponseHeader();
        if (ServerConfig.OPENGZIP) {
            response.setHeader("Content-Encoding", "gzip");
            // 压缩数据
            data = GZIPUtils.compress(data);
        }
        Integer contextLength = 0;
        if (data != null) {
            contextLength = data.length;
        }
        response.setHeader("Content-Length", contextLength.toString());
        StringBuilder responseHeader = new StringBuilder("HTTP/1.1 ").append(httpCode).append(" ").append("\r\n");
        for (String key : response.getHeaders().keySet()) {
            for (String header : response.getHeader(key)) {
                responseHeader.append(key).append(": ").append(header).append("\r\n");
            }
        }
        responseHeader.append("\r\n");
        response.getOutputStream().reset();
        response.getOutputStream().write(responseHeader.toString().getBytes(ServerConfig.ENCODE));
        if (!StringUtil.isNullOrEmpty(data)) {
            response.getOutputStream().write(data);
        }
    }

    public void buildResponseHeader() throws IOException {
        if (response == null) {
            throw new ResponseNotInitException("Response尚未初始化");
        }
        response.setHeader("Connection", "close");
        response.setHeader("Server", "MiniCat/1.0 By Coody");
        if (!response.containsHeader("Content-Type")) {
            response.setHeader("Content-Type", "text/html");
        }
        if (ServerConfig.OPENGZIP) {
            response.setHeader("Content-Encoding", "gzip");
        }
        if (request != null && request.isSessionCread()) {
            String cookie = MessageFormat.format("{0}={1}; HttpOnly", ServerConfig.SESSION_ID_FIELD_NAME,
                    request.getSessionId());
            response.setHeader("Set-Cookie", cookie);
        }
    }

    public abstract void buildRequestHeader();

    private void destroy() {
        if (response != null && response.getOutputStream() != null) {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (request != null && request.getInputStream() != null) {
            try {
                request.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void builder() {
        try {
            buildRequest();
            buildRequestHeader();
            this.response = new HttpServletResponse();
//            MinicatProcess.doService(this);
            response.getOutputStream().write("<!DOCTYPE html>\n" +
                    "<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\"></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=http://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write('<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u='+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ '\" name=\"tj_login\" class=\"lb\">登录</a>');</script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>");
            buildResponse();
        } catch (BadRequestException e) {
            e.printStackTrace();
            try {
                buildResponse(400, "400 bad request");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                buildResponse(500, "error execution");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (PageNotFoundException e) {
            try {
                buildResponse(404, "page not found!");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                buildResponse(500, "error execution");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void flushAndClose() {
        try {
            flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
    }

    protected abstract void buildRequest() throws Exception;

    protected abstract void flush() throws IOException;
}
