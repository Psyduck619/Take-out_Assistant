package takeoutassistant.control;

import takeoutassistant.itf.IUserManager;
import takeoutassistant.model.BeanUser;
import takeoutassistant.util.BaseException;
import takeoutassistant.util.BusinessException;
import takeoutassistant.util.DBUtil;
import takeoutassistant.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserManager implements IUserManager {

    //用户注册
    public BeanUser reg(String userid, String name, String gender, String phone, String email, String city, String pwd, String pwd2) throws BaseException {
        Pattern p = null;
        //判断账号是否合法
        if(userid == null || "".equals(userid)){
            throw new BusinessException("账号不能为空!");
        }
        if(userid.length() > 20){
            throw new BusinessException("登录账号不能超过20字!");
        }
        //判断名字是否合法
        if(name == null || "".equals(name)){
            throw new BusinessException("名字不能为空!");
        }
        if(name.length() > 20){
            throw new BusinessException("名字不能超过20字!");
        }
        //判断电话号是否合法
        if(phone == null || "".equals(phone)){
            throw new BusinessException("名字不能为空!");
        }
        p = Pattern.compile("[0-9]*");
        if(!p.matcher(phone).matches()){
            throw new BusinessException("手机号只能为数字!");
        }
        if(phone.length() != 11){
            throw new BusinessException("手机号必须为11位!");
        }
        //判断邮箱是否合法
        if(email != null && !"".equals(email)){
            p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
            if(!p.matcher(email).matches()){
                throw new BusinessException("邮箱格式错误!");
            }
        }
        //判断密码是否合法
        if(pwd == null || "".equals(pwd)){
            throw new BusinessException("密码不能为空!");
        }
        if(pwd.length() > 16){
            throw new BusinessException("登录密码不能超过16字!");
        }
        if(!pwd.equals(pwd2)){
            throw new BusinessException("密码不相同!");
        }
        //初始化
        Connection conn = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //判断账号是否已存在
            String sql = "select * from tbl_user where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            rs = pst.executeQuery();
            if(rs.next()) throw new BusinessException("登录账号已经存在!");
            rs.close();
            pst.close();
            //实现注册
            sql = "insert into tbl_user(user_id,user_name,user_gender,user_pwd,user_phone,user_email,user_city,user_reg_time,VIP) values(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            pst.setString(2, name);
            pst.setString(3, gender);
            pst.setString(4, pwd);
            pst.setString(5, phone);
            pst.setString(6, email);
            pst.setString(7, city);
            pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setBoolean(9,false);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    //用户登录
    public BeanUser login(String userid, String pwd) throws BaseException {
        //初始化
        BeanUser user = new BeanUser();
        Connection conn = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //判断账号是否存在
            String sql = "select * from tbl_user where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("登录账号不存在!");
            }
            rs.close();
            pst.close();
            //判断密码正确性
            sql = "select user_pwd from tbl_user where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            rs = pst.executeQuery();
            if(rs.next()){
                if(!pwd.equals(rs.getString(1))){
                    rs.close();
                    pst.close();
                    throw new BusinessException("密码错误!");
                }
            }
            rs.close();
            pst.close();
            //设置当前登录用户信息
            user.setUser_id(userid);
            user.setUser_pwd(pwd);
            sql = "select user_name,user_gender,user_phone,user_email,user_city,VIP,VIP_end_time from tbl_user where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,userid);
            rs = pst.executeQuery();
            if(rs.next()){
                user.setUser_name(rs.getString(1));
                user.setUser_gender(rs.getString(2));
                user.setUser_phone(rs.getString(3));
                user.setUser_email(rs.getString(4));
                user.setUser_city(rs.getString(5));
                user.setVIP(rs.getBoolean(6));
                user.setVIP_end_time(rs.getTimestamp(7));
            }
            rs.close();
            pst.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    //用户修改个人信息
    public void modifyUser(BeanUser user, String name, String gender, String phone, String email, String city) throws BaseException{
        Pattern p = null;
        //判断名字是否合法
        if(name == null || "".equals(name)){
            throw new BusinessException("名字不能为空!");
        }
        if(name.length() > 20){
            throw new BusinessException("名字不能超过20字!");
        }
        //判断电话号是否合法
        if(phone == null || "".equals(phone)){
            throw new BusinessException("名字不能为空!");
        }
        p = Pattern.compile("[0-9]*");
        if(!p.matcher(phone).matches()){
            throw new BusinessException("手机号只能为数字!");
        }
        if(phone.length() != 11){
            throw new BusinessException("手机号必须为11位!");
        }
        //判断邮箱是否合法
        if(email != null && !"".equals(email)){
            p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
            if(!p.matcher(email).matches()){
                throw new BusinessException("邮箱格式错误!");
            }
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //实现修改
            sql = "update tbl_user set user_name=?,user_gender=?,user_phone=?,user_email=?,user_city=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,gender);
            pst.setString(3,phone);
            pst.setString(4,email);
            pst.setString(5,city);
            pst.setString(6,user.getUser_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    //用户修改密码
    public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException {
        //判断密码是否合法
        if(oldPwd == null || "".equals(oldPwd) || newPwd == null || "".equals(newPwd) || newPwd2 == null || "".equals(newPwd2)){
            throw new BusinessException("密码不能为空!");
        }
        if(oldPwd.length() > 16 || newPwd.length() > 16 || newPwd2.length() > 16){
            throw new BusinessException("密码不能超过16字!");
        }
        if(!newPwd.equals(newPwd2)){
            throw new BusinessException("两次密码不相同!");
        }
        //初始化
        Connection conn = null;
        String sql = null;
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            //判断密码是否正确
            sql = "select user_pwd from tbl_user where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,user.getUser_id());
            rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("密码错误!");
            }
            rs.close();
            pst.close();
            //实现密码修改
            sql = "update tbl_user set user_pwd=? where user_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,newPwd);
            pst.setString(2,user.getUser_id());
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if(conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    //用户点单

}
