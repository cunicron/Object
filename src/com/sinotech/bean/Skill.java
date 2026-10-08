package com.sinotech.bean;

public class Skill {
    private String skillName;//技能名字
    private int skillCost;//技能消耗的生命值
    //private int skillHP;//恢复血量
    //private int skillATK;//使用技能的攻击力
    private double skillAT;//使用技能提升攻击力的倍率

    public Skill() {
    }

    public Skill(String skillName, int skillCost, double skillAT) {
        this.skillName = skillName;
        this.skillCost = skillCost;
        this.skillAT = skillAT;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillCost() {
        return skillCost;
    }

    public void setSkillCost(int skillCost) {
        this.skillCost = skillCost;
    }

    public double getSkillAT() {
        return skillAT;
    }

    public void setSkillAT(double skillAT) {
        this.skillAT = skillAT;
    }

    //技能输出的伤害
    public int skillEffects(Character character){
        return (int)(character.getATK() * skillAT);
    }
    //技能恢复的生命值--参数为随机数，通过随机数来判断恢复的生命值
    public int RandomHP(int random){
        //获取随机数0-恢复0，1-恢复10，2-恢复20；
        int HP = 0;
        if (random == 0) {
        } else if (random == 1) {
            HP = 10;
        } else {
            HP = 20;
        }
        return HP;
    }
}
