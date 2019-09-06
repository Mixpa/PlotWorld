package mixpa.qq514518274.config;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Message {
    @Getter
    private static String prefix;
    @Getter
    private static String cantBuildInRoad;
    @Getter
    private static String cantClaimInRoad;
    @Getter
    private static String resetUsage;
    @Getter
    private static String cantResetInRoad;
    @Getter
    private static String noMine;
    @Getter
    private static String noMoney;
    @Getter
    private static String mustBeAPlayer;
    @Getter
    private static String waitCoolDowns = "&7你必须等候{cool_downs}才能reset plot地区哦";
    @Getter
    private static List<String> helpMessage;
    @Getter
    private static String second = "秒";
    @Getter
    private static String minute = "分";
    @Getter
    private static String hour = "小时";
    @Getter
    private static String day = "天";

    public Message(final File file) throws IllegalAccessException, FileNotFoundException {
        if (!file.exists())
            throw new FileNotFoundException("file is not find!");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Field field : Message.class.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            if (config.contains(name)) {
                field.set(null, config.get(name));
            } else throw new NullPointerException("配置文件message.yml中的" + name + "配置不存在！");
        }
        for (Field field : Message.class.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            if (name.equals("prefix"))
                continue;
            if (name.equals("helpMessage"))
                continue;
            if (name.equals("second")||name.equals("minute")||name.equals("hour")||name.equals("day"))
                continue;
            field.set(null, transString((String) field.get(null)));
        }
        helpMessage = transString(helpMessage);
    }

    public static String getNoMoney(Double money) {
        return noMoney.replaceAll("\\{money}", money.toString());
    }

    public static String getNoMine(String mineName) {
        return noMine.replaceAll("\\{mine}", mineName);
    }

    public static String getWaitCoolDowns(long coolDowns) {
        return waitCoolDowns.replaceAll("\\{cool_downs}", coolDownsToString(coolDowns));
    }

    private List<String> transString(List<String> list) {
        ArrayList<String> help = Lists.newArrayList();
        for (String var : list) {
            help.add(ChatColor.translateAlternateColorCodes('&', var));
        }
        return help;
    }

    private String transString(String var) {
        return ChatColor.translateAlternateColorCodes('&', prefix + var);
    }

    /**
     * 将时间转换为天 小时 分 秒单位
     * @param coolDowns 单位是秒的时间
     * @return 转换的时间文本
     */
    private static String coolDownsToString(long coolDowns){
        String time;
        if (coolDowns < 60)
            time = coolDowns + second;
        else if (coolDowns < 60 * 60)
            time = coolDowns / 60 + minute + coolDowns % 60 + second;
        else if (coolDowns < 60 * 60 * 60)
            time = coolDowns / (60 * 60) + hour + coolDownsToString(coolDowns % (60 * 60));
        else time = coolDowns / (60 * 60 * 60) + day + coolDownsToString(coolDowns % (60 * 60 * 60));
        return time;
    }
}
