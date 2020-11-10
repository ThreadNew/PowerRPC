package tn.threadnew.powerrpc.rpc.netty.annotation.impl;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/2 20:06
 * @Version: 1.0
 */
public class RpcInfos {
    private Class clazz;
    private String group;
    private String verion;
    private String simpleName;

    public RpcInfos() {
    }

    public RpcInfos(Class clazz, String group, String verion, String simpleName) {
        this.clazz = clazz;
        this.group = group;
        this.verion = verion;
        ;
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return "RpcInfos{" +
                "clazz=" + clazz +
                ", group='" + group + '\'' +
                ", verion='" + verion + '\'' +
                ", simpleName='" + simpleName + '\'' +
                '}';
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVerion() {
        return verion;
    }

    public void setVerion(String verion) {
        this.verion = verion;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
}
