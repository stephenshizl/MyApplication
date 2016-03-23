package com.example.administrator.androidscroll.other;

import android.os.Build;

/**
 *  获取系统的一些信息
 * Created by Administrator on 2015/12/29.
 */
public class SystemInfor {

    public static String makeInfoString(String[] description, String[] prop) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prop.length; i++) {
            sb.append(description[i]).append(" : ").append(prop[i]).append("\r\n");
        }
        return sb.toString();
    }

    public static String getBuildInfo() {
        // 主板
        String board = Build.BOARD;
        // Android 系统定制商
        String brand = Build.BRAND;
        // CPU 指令集
        String supported_abis = Build.SUPPORTED_ABIS[0];
        // 设备参数
        String device = Build.DEVICE;
        // 显示屏参数
        String display = Build.DISPLAY;
        // 唯一编号
        String fingerprint = Build.FINGERPRINT;
        // 硬件序列号
        String serial = Build.SERIAL;
        // 修订版本列表
        String id = Build.ID;
        // 硬件制造商
        String manufacturer = Build.MANUFACTURER;
        // 版本
        String model = Build.MODEL;
        // 硬件名
        String hardware = Build.HARDWARE;
        // 手机产品名
        String product = Build.PRODUCT;
        // 描述 Build 的标签
        String tags = Build.TAGS;
        // Builder 类型
        String type = Build.TYPE;
        // 当前开发代号
        String codename = Build.VERSION.CODENAME;
        // 源码控制版本号
        String incremental = Build.VERSION.INCREMENTAL;
        // 版本字符串
        String release = Build.VERSION.RELEASE;
        // 版本号
        String sdk_int = "" + Build.VERSION.SDK_INT;
        // Host 值
        String host = Build.HOST;
        // User 名
        String user = Build.USER;
        // 编译时间
        String time = "" + Build.TIME;
        String[] prop = {
                board,
                brand,
                supported_abis,
                device,
                display,
                fingerprint,
                serial,
                id,
                manufacturer,
                model,
                hardware,
                product,
                tags,
                type,
                codename,
                incremental,
                release,
                sdk_int,
                host,
                user,
                time
        };
        String[] description = {
                "board",
                "brand",
                "supported_abis",
                "device",
                "display",
                "fingerprint",
                "serial",
                "id",
                "manufacturer",
                "model",
                "hardware",
                "product",
                "tags",
                "type",
                "codename",
                "incremental",
                "release",
                "sdk_int",
                "host",
                "user",
                "time"
        };
        return makeInfoString(description, prop);
    }

    public static String getSystemPropertyInfo() {
        // OS 版本
        String os_version = System.getProperty("os.version");
        // OS 名称
        String os_name = System.getProperty("os.name");
        // OS 架构
        String os_arch = System.getProperty("os.arch");
        // Home 属性
        String user_home = System.getProperty("user.home");
        // Name 属性
        String user_name = System.getProperty("user.name");
        // Dir 属性
        String user_dir = System.getProperty("user.dir");
        // 时区
        String user_timezone = System.getProperty("user.timezone");
        // 路径分隔符
        String path_separator = System.getProperty("path.separator");
        // 行分隔符
        String line_separator = System.getProperty("line.separator");
        // 文件分隔符
        String file_separator = System.getProperty("file.separator");
        // Java vender URL 属性
        String java_vendor_url = System.getProperty("java.vendor.url");
        // Java Class 路径
        String java_class_path = System.getProperty("java.class.path");
        // Java Class 版本
        String java_class_version = System.getProperty("java.class.version");
        // Java Vender 属性
        String java_vendor = System.getProperty("java.vendor");
        // Java 版本
        String java_version = System.getProperty("java.version");
        // Java Home　属性
        String java_home = System.getProperty("java_home");
        String[] prop = {
                os_version,
                os_name,
                os_arch,
                user_home,
                user_name,
                user_dir,
                user_timezone,
                path_separator,
                line_separator,
                file_separator,
                java_vendor_url,
                java_class_path,
                java_class_version,
                java_vendor,
                java_version,
                java_home
        };
        String[] description = {
                "os_version",
                "os_name",
                "os_arch",
                "user_home",
                "user_name",
                "user_dir",
                "user_timezone",
                "path_separator",
                "line_separator",
                "file_separator",
                "java_vendor_url",
                "java_class_path",
                "java_class_version",
                "java_vendor",
                "java_version",
                "java_home"
        };
        return makeInfoString(description, prop);
    }
}
