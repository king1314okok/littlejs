package com.oudake.common.msg;

import java.io.Serializable;

/**
 * @author wangyi
 */
public class MailMsg implements Serializable {

    /** 不需要修改formAddr 默认为配置文件中的formAddr*/
    private String formAddr;
    private String toAddr;
    private String title;
    private String context;
    private boolean isHtml = true;

    public String getFormAddr() {
        return formAddr;
    }

    public void setFormAddr(String formAddr) {
        this.formAddr = formAddr;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    @Override
    public String toString() {
        return "MailMsg{" +
                "formAddr='" + formAddr + '\'' +
                ", toAddr='" + toAddr + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", isHtml=" + isHtml +
                '}';
    }
}
