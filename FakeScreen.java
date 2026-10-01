package com.fakedoomsday.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import java.util.*;

public class FakeScreen extends Screen {
    private final String[] cats={"COMBAT","MOVEMENT","PLAYER","RENDER","WORLD","HUD","MISC"};
    private final Map<String,List<String>> mods=new LinkedHashMap<>();
    private final Set<String> enabled=new HashSet<>();
    private String cat="COMBAT";

    public FakeScreen(){
        super(Text.literal("Fake Doomsday"));
        mods.put("COMBAT",List.of("KillAura","AimAssist","AutoClicker","Criticals","Velocity","Reach","Hitboxes","NoSwing"));
        mods.put("MOVEMENT",List.of("Speed","Fly","Sprint","NoSlow","Step","HighJump","Strafe","Jesus"));
        mods.put("PLAYER",List.of("NoFall","FastPlace","FastBreak","InventoryMove","AutoTool","ChestStealer","Scaffold","Freecam"));
        mods.put("RENDER",List.of("ESP","FullBright","Tracers","Nametags","Chams","NoHurtCam","NoFog","XRay"));
        mods.put("WORLD",List.of("TimeChanger","Weather","CaveFinder","BlockESP","Waypoints","Nuker","AntiBot","Timer"));
        mods.put("HUD",List.of("ArrayList","Coordinates","FPS","CPS","Keystrokes","ArmorHUD","Watermark","Notifications"));
        mods.put("MISC",List.of("Config","IRC","ClickGUI","Sounds","StreamProof","Panic","FriendManager","Themes"));
    }

    @Override public void render(DrawContext c,int mx,int my,float d){
        c.fill(0,0,width,height,0xD906070A);
        c.fill(28,22,width-28,height-22,0xFF111217);
        c.fill(28,22,178,height-22,0xFF0C0D11);
        c.drawTextWithShadow(textRenderer,Text.literal("DOOMSDAY"),48,42,0xFFFF4D5A);
        c.drawTextWithShadow(textRenderer,Text.literal("FAKE CLIENT"),48,58,0xFF777A82);

        int cy=86;
        for(String x:cats){
            boolean sel=x.equals(cat);
            if(sel)c.fill(40,cy-5,166,cy+18,0xFF321A20);
            c.drawTextWithShadow(textRenderer,Text.literal(x),53,cy,sel?0xFFFF6670:0xFF9A9DA5);
            cy+=29;
        }

        c.drawTextWithShadow(textRenderer,Text.literal(cat),205,43,0xFFE9E9EC);
        c.drawTextWithShadow(textRenderer,Text.literal("VISUAL SIMULATION"),205,60,0xFF777A82);

        int idx=0;
        for(String m:mods.get(cat)){
            int col=idx%3,row=idx/3,bx=205+col*225,by=84+row*58;
            boolean on=enabled.contains(m);
            c.fill(bx,by,bx+205,by+45,on?0xFF351A20:0xFF1B1C22);
            c.drawTextWithShadow(textRenderer,Text.literal(m),bx+12,by+9,0xFFE5E6E8);
            c.drawTextWithShadow(textRenderer,Text.literal(on?"ON":"OFF"),bx+12,by+27,on?0xFFFF5963:0xFF6F727A);
            c.drawTextWithShadow(textRenderer,Text.literal("BIND"),bx+158,by+27,0xFF555861);
            idx++;
        }
        c.drawTextWithShadow(textRenderer,Text.literal("Right Shift • GUI    Left Click • Toggle    ESC • Close"),
            205,height-42,0xFF666A73);
        c.drawTextWithShadow(textRenderer,Text.literal("All modules are cosmetic and have NO gameplay effect."),
            205,height-27,0xFF555860);
        super.render(c,mx,my,d);
    }

    @Override public boolean mouseClicked(double mx,double my,int button){
        if(button!=0)return super.mouseClicked(mx,my,button);
        if(mx>=40&&mx<=166&&my>=80&&my<80+cats.length*29){
            int i=(int)((my-80)/29); if(i>=0&&i<cats.length){cat=cats[i];return true;}
        }
        int idx=0;
        for(String m:mods.get(cat)){
            int col=idx%3,row=idx/3,bx=205+col*225,by=84+row*58;
            if(mx>=bx&&mx<=bx+205&&my>=by&&my<=by+45){
                if(!enabled.add(m))enabled.remove(m);
                return true;
            }
            idx++;
        }
        return super.mouseClicked(mx,my,button);
    }
    @Override public boolean shouldPause(){return false;}
}
