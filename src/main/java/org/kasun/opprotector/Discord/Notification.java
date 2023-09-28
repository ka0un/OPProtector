package org.kasun.opprotector.Discord;

import org.kasun.opprotector.OPProtector;

import java.awt.*;
import java.sql.Timestamp;

public class Notification {
    private String username;
    private String avatar_url;
    private String colour;
    private String title;
    private String description;
    private OPProtector plugin;

    public Notification(String username, NotificationType type, String description) {
        plugin = OPProtector.getInstance();
        this.username = username;
        this.title = title;
        this.description = description;
        avatar_url = "https://minotar.net/avatar/" + username + "/64.png";

        switch (type){
            case JOIN:
                title = "<:PlusOne:566099198118854676>  Operator Joined";
                colour = "#B3C998";
                this.description = "``` " + username + "```";
                break;
            case LEAVE:
                title = "<:MinusOne:566099197603086348>  Operator Left";
                colour = "#B3C998";
                this.description = "``` " + username + "```";
                break;
            case AUTH_FAIL:
                title = "<:TeamYellow:827068159252103179>  Operator Failed to Authenticate";
                colour = "#FFFC31";
                break;
            case AUTH_SUCCESS:
                title = "<:TeamGreen:827068159088787516>  Operator Authenticated";
                colour = "#7BB61E";
                this.description = "``` " + username + "```";
                break;
            case UNAUTH_ACCESS:
                title = "<:TeamRed:827068159390646292>  Unauthenticated Access Attempt";
                colour = "#E94F37";
                break;
        }

        if(!(plugin.getMainManager().getConfigManager().getMainConfig().discord_webhook == null || plugin.getMainManager().getConfigManager().getMainConfig().discord_webhook == "" || plugin.getMainManager().getConfigManager().getMainConfig().discord_webhook == " " || plugin.getMainManager().getConfigManager().getMainConfig().discord_webhook == "-")){
            send();
        }

    }

    private void send(){
        DiscordWebhook webhook = new DiscordWebhook(plugin.getMainManager().getConfigManager().getMainConfig().discord_webhook);
        webhook.setUsername("OPProtector");
        webhook.setAvatarUrl("https://raw.githubusercontent.com/ka0un/OPProtector/master/icon.png");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setThumbnail(avatar_url)
                .setFooter(new Timestamp(System.currentTimeMillis()).toString())
                .setTitle(title)
                .setDescription(description)
                .setColor(Color.decode(colour)));

        try{
            webhook.execute();
        }catch (Exception e) {
            plugin.getLogger().warning("Failed to send notification to discord");
        }
    }
}
