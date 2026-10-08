package com.sinotech.bean;

import java.util.ArrayList;

public class Character {
    String characterName;//角色名，与玩家用户名一致
    int HP;//生命值
    int maxHP;//最大生命值
    int ATK;//角色攻击力
    int DEF;//角色防御力
    ArrayList<Skill> list_skill = new ArrayList<>();//角色技能列表--创建内部类--使用对象，每种对象对应一种技能，使用技能时调用对应对象的方法

    public Character() {
    }

    public Character(String characterName, int HP, int maxHP, int ATK, int DEF, ArrayList<Skill> list_skill) {
        this.characterName = characterName;
        this.HP = HP;
        this.maxHP = maxHP;
        this.ATK = ATK;
        this.DEF = DEF;
        this.list_skill = list_skill;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
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

    public ArrayList<Skill> getList_skill() {
        return list_skill;
    }

    //set方法可以在外部定义一个类型为Skill的集合，之后将这个集合用set方法赋值给这个类的对象
    public void setList_skill(ArrayList<Skill> list_skill) {
        this.list_skill = list_skill;
    }
}
