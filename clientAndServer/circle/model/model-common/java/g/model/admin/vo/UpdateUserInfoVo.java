package g.model.admin.vo;

import org.soul.model.msg.notice.po.NoticeContactWay;

import java.util.Date;

/**
 * Created by cj on 15-8-28.
 */
public class UpdateUserInfoVo {
    private String realName;
    private String phone;
    private Integer phoneId;
    private String email;
    private Integer emailId;
    private String sex;
    private Date birthday;
    private NoticeContactWay skype;
    private NoticeContactWay msn;
    private NoticeContactWay qq;
    private String constellation;
    private Integer protectionId;
    private String question1;
    private String answer1;
    private String nickname;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public Integer getProtectionId() {
        return protectionId;
    }

    public void setProtectionId(Integer protectionId) {
        this.protectionId = protectionId;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public NoticeContactWay getSkype() {
        return skype;
    }

    public void setSkype(NoticeContactWay skype) {
        this.skype = skype;
    }

    public NoticeContactWay getMsn() {
        return msn;
    }

    public void setMsn(NoticeContactWay msn) {
        this.msn = msn;
    }

    public NoticeContactWay getQq() {
        return qq;
    }

    public void setQq(NoticeContactWay qq) {
        this.qq = qq;
    }
}
