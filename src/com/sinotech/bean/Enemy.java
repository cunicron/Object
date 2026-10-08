package com.sinotech.bean;

public class Enemy {
    String name;//敌人名称
    int HP;//敌人生命值
    int HPMax;//敌人最大生命值
    int ATK;//敌人攻击力
    int DEF;//敌人防御力
    boolean state;//用于重装坦克的技能判断-true为正常状态，false为使用技能状态
    double at;//技能伤害倍率
    String enemySkillName;//敌人技能名字

    public Enemy() {
    }

    public Enemy(String name, int HP, int HPMax, int ATK, int DEF, boolean state, double at, String enemySkillName) {
        this.name = name;
        this.HP = HP;
        this.HPMax = HPMax;
        this.ATK = ATK;
        this.DEF = DEF;
        this.state = state;
        this.at = at;
        this.enemySkillName = enemySkillName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHPMax() {
        return HPMax;
    }

    public void setHPMax(int HPMax) {
        this.HPMax = HPMax;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getAt() {
        return at;
    }

    public void setAt(double at) {
        this.at = at;
    }

    public String getEnemySkillName() {
        return enemySkillName;
    }

    public void setEnemySkillName(String enemySkillName) {
        this.enemySkillName = enemySkillName;
    }

    //技能效果方法
    public int skillEffects(Enemy enemy) {
        //状态
        /*if (enemy.getName().equals("重装坦克"))
            enemy.setState(false);*/
        //出伤量--战士1.5--刺客1.5--法师1.8
        return (int) (enemy.getATK() * at);
    }

}
