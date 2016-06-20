package com.redsea.controller.api;
//package com.redsea.controller.api;
//
//import java.util.Date;
//
//import com.alibaba.fastjson.JSONObject;
//import com.jfinal.kit.StrKit;
//import com.jfinal.log.Logger;
//
//import com.redsea.common.Consts;
//import com.redsea.ext.base.BaseController;
//import com.redsea.model.User;
//import com.redsea.model.WbLogin;
//
///**
// * qq登录api
// * @author Rocky
// * @date 2013-5-14 下午5:08:12
// */
//public class QQLoginController extends BaseController {
//    
//    private static final String SESSION_STATE = "_SESSION_STATE_QQ_";
//    
//    public void index() {
//        try {
//            String state = TokenUtil.randomState();
//            setSessionAttr(SESSION_STATE, state);
//            redirect(OauthQQ.me().getAuthorizeUrl(state));
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            redirect("/");
//        }
//    }
//
//    /**
//     * 腾讯回调
//     * @Title: callback
//     * @param     设定文件
//     * @return void    返回类型
//     * @throws
//     * 返回json:<url>http://wiki.connect.qq.com/get_user_info</url>
//     */
//    public void callback() {
//        String code = getPara("code");
//        String state = getPara("state");
//        String session_state = getSessionAttr(SESSION_STATE);
//        // 取消了授权
//        if (StrKit.isBlank(state) || StrKit.isBlank(session_state) || !state.equals(session_state) || StrKit.isBlank(code)) {
//            redirect("/admin");
//            return;
//        }
//        removeSessionAttr(SESSION_STATE);
//        try{
//            JSONObject userInfo = OauthQQ.me().getUserInfoByCode(code);
//            log.error(userInfo.toJSONString());
//            String type = "qq";
//            String openid = userInfo.getString("openid");
//            String nickname = userInfo.getString("nickname");
//            String photoUrl = userInfo.getString("figureurl_2");
//            WbLogin login = WbLogin.dao.findByOpenID(openid, type);
//            log.error("login1:\t" + login);
//            if(null == login) {
//                login = new WbLogin();
//                login.set(WbLogin.OPENID, openid);
//                login.set(WbLogin.TYPE, type);
//                login.set(WbLogin.HEAD_PHOTO, photoUrl);
//                login.set(WbLogin.CREATETIME, new Date());
//                login.set(WbLogin.STATUS, WbLogin.STATUS_N);
//                login.set(WbLogin.NICKNAME, nickname).save();
//            }
//            log.error("login2:\t" + login);
//            // 是否邮件校验通过
//            int status = login.getInt(WbLogin.STATUS);
//            if (null != login.getInt(WbLogin.ID) && WbLogin.STATUS_N == status) {
//                if (null == login.getInt(WbLogin.USERID)) {
//                    setAttr("nouser", true);
//                }
//                // 跳转到绑定页
//                setAttr("id", login.getInt(WbLogin.ID));
//                setAttr("type", type);
//                setAttr("nickname", nickname);
//                setAttr("photourl", photoUrl);
//                render("binding.vm");
//                return;
//            }
//            if (WbLogin.STATUS_Y == status) {
//                User user = User.dao.findById(login.getInt(WbLogin.USERID));
//                setSessionAttr(Consts.USER_SESSION, user);
//            }
//        }catch(Exception e){
//            log.error(e.getMessage());
//        }
//        redirect("/admin");
//    }
//}
