package cn.ttitcn.common.enums;
/**
 * 用户类型
 */
public enum UserType {

    TEACHER(0, "教师"), STUDENT(1, "学生"), GUARDIAN(2, "家长");
    
    private final Integer code;
    private final String info;

    UserType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    
}
