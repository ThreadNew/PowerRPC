package tn.threadnew.powerrpc.register.loadblance;

/**
 * @Author: ThreadNew
 * @Description: TODO 记录节点信息 连接和权重
 * @Date: 2020/10/25 8:13
 * @Version: 1.0
 */
public class NodeValue {
    private String ip;
    private int port;
    private double weight;

    public NodeValue() {
    }

    public NodeValue(String ip, int port, double weight) {
        this.ip = ip;
        this.port = port;
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "NodeValue{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
