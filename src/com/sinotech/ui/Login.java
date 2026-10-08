package com.sinotech.ui;

import com.sinotech.bean.Character;
import com.sinotech.bean.Enemy;
import com.sinotech.bean.Skill;
import com.sinotech.bean.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    ArrayList<User> list_user = new ArrayList<>();//登录用户集合
    User user = new User();
    Scanner sc = new Scanner(System.in);
    Random r = new Random();
    int number_list_denglu = -1;//用于定位当前登录的用户在列表中的哪个位置--用于游戏角色名赋值
    ArrayList<Enemy> list_enemy = new ArrayList<>();//游戏内敌人集合
    int randomSkillHP = 3;//血量随机数范围--必用
    int randomSkillEnemy = 4;//敌人随机选取随机数范围--非必用
    int CC_victory = 0;//战斗胜利的场次


    public void start() {
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");

            String choose = sc.next();

            //登录前
            switch (choose) {
                case "1" -> login();
                case "2" -> register();
                case "3" -> {
                    System.out.println("用户选择了退出操作");
                    System.exit(0);//状态0为正常退出操作，非0为异常退出操作
                }
                default -> System.out.println("输入有误，请重新输入");
            }
        }
    }

    //用户登录操作
    public void login() {
        System.out.println("用户选择了登录操作");
        int stateNumber = 0;//定义状态number，如果为3，则禁用账户，状态为false
        //登录时先判断状态是否为true,通过用户名判断其状态
        System.out.println("请输入用户名：");
        String str5 = sc.next();

        while (selectUser(str5) < 0) {
            if (selectUser(str5) == -1) {
                System.out.println("当前用户名不存在，请重新输入用户名");
                str5 = sc.next();
            }
        }
        //输入密码并判断账户是否被禁用
        if (orState(selectUser(str5))) {
            //未被冻结则正常进行登录操作，如果输入错误则将stateNumber进行加1，如果为3，则设置集合内的状态为false
            userLogin(stateNumber, selectUser(str5));
        } else {
            System.out.println("当前用户已被冻结，请联系管理员：XXX-XXXXX-XXX");
        }
        loginEnd();

    }

    //用户注册操作
    public void register() {
        System.out.println("用户选择了注册操作");

        //用户名创建
        Boolean b = false;
        while (!b) {
            b = username(b);
        }
        System.out.println("恭喜用户名创建成功");

        //密码创建
        password();
        System.out.println("恭喜用户密码创建成功");

        //id随机生成，game+5位随机数字
        id();

        System.out.println("恭喜注册成功");
        System.out.println("用户名：" + user.getUsername() + ", 密码为：" + user.getPassword() + ", id为：" + user.getId());
    }

    //用户注册用户名操作
    public boolean username(boolean number) {//number判断是否为重复用户名，0不重复，1重复
        System.out.println("用户名不可以重复，长度为3~16位");
        System.out.println("用户名只能由字母、数字组成，不能是纯数字");
        System.out.println("请输入用户名：");

        String str1 = sc.next();
        //判断长度是否未3~16位
        userLength(str1);

        //判断是否由小写字母、数字以及不能由纯数字组成
        number = false;
        while (!number) {
            int num = 0;

            //判断字符串中的每一位是不是符合要求
            for (int i = 0; i < str1.length(); i++) {
                char ch = str1.charAt(i);
                if ((ch >= 'a' && ch <= 'z')) {
                    number = true;
                } else if (ch >= '0' && ch <= '9') {
                    num++;
                    number = true;
                } else {
                    number = false;
                    System.out.println("用户名只能由小写字母和数字组成，请重新输入用户名");
                    str1 = sc.next();
                    userLength(str1);
                }
            }
            if (num == str1.length()) {
                System.out.println("用户名为纯数字，请重新输入用户名");
                str1 = sc.next();
                userLength(str1);
                number = false;
            }
        }

        //判断用户名在集合中是否唯一，用User对象类型集合判断
        for (int i = 0; i < list_user.size(); i++) {
            if (list_user.get(i).getUsername().equals(str1)) {
                System.out.println("该用户名已存在，请重新输入");
                number = false;
                break;
            }
        }
        if (number == true) {
            user.setUsername(str1);
            list_user.add(user);
        }
        return number;
    }

    //用户注册判断用户名长度
    public void userLength(String str1) {
        while (str1.length() > 16 || str1.length() < 3) {
            if (str1.length() > 16) {
                System.out.println("用户名长度过长，请重新输入");
                str1 = sc.next();
            } else {
                System.out.println("用户名长度过短，请重新输入");
                str1 = sc.next();
            }
        }
    }

    //用户名注册密码操作
    public void password() {
        System.out.println("密码长度为3~8位");
        System.out.println("密码只能是数字加小写字母的组合，不能是纯数字");
        System.out.println("请输入密码：");
        String str2 = sc.next();
        //判断字符串长度是否位3~8位
        passLength(str2);

        //判断密码是否是由纯数字组成
        str2 = passCode(str2);

        System.out.println("请再次输入密码：");
        String str3 = sc.next();
        while (!str3.equals(str2)) {
            System.out.println("两次密码不一样，请重新输入：");
            str3 = sc.next();
        }
        user.setPassword(str2);
    }

    //用户名注册密码操作--判断长度是否符合
    public void passLength(String str2) {
        while (str2.length() > 8 || str2.length() < 3) {
            if (str2.length() > 8) {
                System.out.println("密码长度过长，请重新输入");
                str2 = sc.next();
            } else {
                System.out.println("密码长度过短，请重新输入");
                str2 = sc.next();
            }
        }
    }

    //用户名注册密码操作--判断密码是否是由纯数字组成
    public String passCode(String str2) {
        boolean number = false;
        int num = 0;
        while (!number) {
            num = 0;
            for (int i = 0; i < str2.length(); i++) {
                char ch = str2.charAt(i);
                if ((ch >= 'a' && ch <= 'z')) {
                    number = true;
                } else if (ch >= '0' && ch <= '9') {
                    num++;
                    number = true;
                } else {
                    number = false;
                    System.out.println("密码只能由小写字母和数字组成，请重新输入密码");
                    str2 = sc.next();
                    passLength(str2);
                }
            }
            if (num == str2.length()) {
                number = false;
                System.out.println("密码不能是纯数字，请重新输入密码");
                str2 = sc.next();
                passLength(str2);
            }
        }
        return str2;
    }

    //用户名注册id生成操作
    public void id() {
        String str4 = "game";
        int num = r.nextInt(100000);
        if (num <= 9) {
            str4 = str4 + "0000" + num;
        } else if (num > 9 && num <= 99) {
            str4 = str4 + "000" + num;
        } else if (num > 99 && num <= 999) {
            str4 = str4 + "00" + num;
        } else if (num > 999 && num <= 9999) {
            str4 = str4 + "0" + num;
        } else {
            str4 = str4 + num;
        }
        user.setId(str4);//设置user对象名的id参数
        user.setState(true);//设置状态为可用状态
    }

    //用户名登录操作--查找对应用户名所在集合的位置，返回索引值
    public int selectUser(String str5) {
        int emp = -1;
        for (int i = 0; i < list_user.size(); i++) {
            if (list_user.get(i).getUsername().equals(str5)) {
                emp = i;
                break;
            }
        }
        return emp;
    }

    //用户名登录操作--得到索引值之后，通过调用索引值，来得到当前用户名的状态
    public boolean orState(int test) {
        boolean b = true;
        if (list_user.get(test).isState()) {
        } else {
            b = false;
        }
        return b;
    }

    //用户名登录操作--确定用户名不被冻结时，进行正常登录操作
    public void userLogin(int stateNumber, int index) {
        System.out.println("请输入密码：");
        String str6 = sc.next();
        //验证码操作
        code();
        //对密码进行校验
        if (list_user.get(index).getPassword().equals(str6)) {
            System.out.println("用户名和密码正确，欢迎~");
            this.number_list_denglu = index;
        } else {
            if (stateNumber < 2) {
                stateNumber++;
                System.out.println("密码错误，请重新输入");
                userLogin(stateNumber, index);
            } else if (stateNumber == 2) {
                list_user.get(index).setState(false);
                System.out.println("当前用户已被冻结，请联系管理员：XXX-XXXXX-XXX");
            }
        }
    }

    //用户名登录操作--验证码生成并校验
    public boolean code() {
        //生成验证码
        String strCode = Verification();
        //打印验证码
        System.out.println(strCode);
        //提示输入验证码
        printCode(strCode);
        return true;
    }

    //用户名登录操作--验证码生成并校验--生成校验码
    public String Verification() {
        //生成校验码
        //随机数1：数字存放的位置
        int num1 = r.nextInt(5);
        //随机数2：定义要存放的数字
        int num2 = r.nextInt(10);
        //定义生成的验证码
        String strCode = "";
        for (int i = 0; i < 5; i++) {
            if (i == num1) {
                char ch1 = (char) (num2 + 48);
                strCode = strCode + ch1;
            } else {
                //判断当前随机数是让生成大写还是生成小写，0小写，1大写
                if (r.nextInt(2) == 0) {
                    char ch2 = (char) (r.nextInt(26) + 97);
                    strCode = strCode + ch2;
                } else {
                    char ch3 = (char) (r.nextInt(26) + 65);
                    strCode = strCode + ch3;
                }
            }
        }
        return strCode;
    }

    //用户名登录操作--验证码生成并校验--输入验证码
    public void printCode(String strCode) {
        System.out.println("请输入验证码：");
        String str6 = sc.next();
        if (strCode.equals(str6)) {
        } else {
            System.out.println("验证码输入错误，请重新输入");
            strCode = Verification();
            System.out.println(strCode);
            printCode(strCode);
        }
    }

    //用户登录后操作--初始化各种数值和角色
    public void loginEnd() {
        //登录后初始化和分配角色
        //number_list--当前登录的账号在用户名集合中的位置
        //先创建角色对象
        Character character = new Character();
        //初始化角色------------(暂定没有技能)
        charLogin(character, number_list_denglu);
        //初始化技能
        //普通攻击-消耗0-攻击力为当前角色攻击力攻击力
        Skill skill1 = new Skill("普通攻击", 0, 1);
        //强力一击-消耗10-攻击为当前角色攻击力的1.8倍
        Skill skill2 = new Skill("强力一击", 10, 1.8);
        //生命汲取-消耗10-攻击0
        Skill skill3 = new Skill("生命汲取", 10, 1);
        //将技能赋值给角色对象中的集合中
        Assignment(character, skill1, skill2, skill3);
        //角色分配属性
        System.out.println("初始角色有20点自由属性，请分配");
        assign(character);
        //角色分配完毕
        System.out.println("\uD83C\uDF1F 角色属性：" + character.getCharacterName() + " [HP:" + character.getHP() + "/" + character.getMaxHP() + ", ATK:" + character.getATK() + ", DEF：" + character.getDEF() + "]");
        System.out.println("\uD83C\uDF1F 角色技能：" + character.getList_skill().get(0).getSkillName() + "，" + character.getList_skill().get(1).getSkillName() + "，" + character.getList_skill().get(2).getSkillName());
        //初始化敌人--Enemy集合
        charEnemy(list_enemy);
        //游戏开始
        gameBegin(character,list_enemy);
    }

    //角色初始化操作
    public void charLogin(Character character, int number_list_denglu) {
        character.setHP(100);
        character.setMaxHP(100);
        character.setATK(10);
        character.setDEF(0);
        character.setCharacterName(list_user.get(number_list_denglu).getUsername());
    }

    //初始化技能--获取随机数
    public int Random(int number) {
        return r.nextInt(number);
    }

    //角色初始化操作--将技能赋值给角色对象中的集合中
    public void Assignment(Character character, Skill skill1, Skill skill2, Skill skill3) {
        character.getList_skill().add(skill1);
        character.getList_skill().add(skill2);
        character.getList_skill().add(skill3);
    }

    //角色属性分配
    public void assign(Character character) {
        System.out.println("创建你的角色：");
        System.out.println("角色初始属性：" + character.getCharacterName() + " [HP:" + character.getHP() + "/" + character.getMaxHP() + ", ATK:" + character.getATK() + ", DEF：" + character.getDEF() + "]");
        System.out.println("请分配各项属性值，共20点");
        System.out.println("1、生命值（每点+10 HP）");
        System.out.println("2、攻击力（每点+ 2 AYK）");
        System.out.println("3、防御力（每点+ 1 DEF）");
        int numberall = 20;//分配属性点20
        assignApiont(numberall, character);

        System.out.println("属性分配完毕，当前角色属性为：");
        System.out.println("角色名：" + character.getCharacterName() + ", 生命值：" + character.getHP() + ", 攻击力：" + character.getATK() + ", 防御力：" + character.getDEF());
    }

    //分配属性点的方法--递归--点数未分配完时需要递归--超出分配后直接下一步
    public void assignApiont(int numberall, Character character) {
        //分配给生命值
        System.out.println("请分配生命值属性（一点属性等于10生命值，剩余点数：" + numberall + "）");
        int hp = printPT(numberall);

        character.setHP(character.getHP() + hp * 10);
        character.setMaxHP(character.getMaxHP() + hp * 10);
        numberall = numberall - hp;
        //分配攻击力
        System.out.println("请分配攻击力属性（一点属性等于2攻击力，剩余点数：" + numberall + "）");
        int atk = printPT(numberall);
        character.setATK(character.getATK() + atk * 2);
        numberall -= atk;
        //分配防御力
        System.out.println("请分配防御力属性（一点属性等于1防御力，剩余点数：" + numberall + "）");
        int def = printPT(numberall);
        character.setDEF(character.getDEF() + def);
        numberall -= def;

        //如果未分配完，则继续递归分配
        if (numberall == 0) {
            //结束
            System.out.println("角色创建成功！");
        } else {
            assignApiont(numberall, character);
        }
    }

    //判断输入的数字是否可以分配
    public int printPT(int numberall) {
        int test = sc.nextInt();
        while (!(test <= numberall & test >= 0)) {
            System.out.println("分配数值不符合要求，请重新输入：");
            test = sc.nextInt();
        }
        return test;
    }

    //创建敌人类型
    public void charEnemy(ArrayList<Enemy> list_enemy) {
        //初级战士
        Enemy enemy1 = new Enemy("初级战士", 80, 80, 15, 10, true, 1.5,"猛击");
        //敏捷刺客
        Enemy enemy2 = new Enemy("敏捷刺客", 60, 60, 20, 5, true, 1.5,"快速攻击");
        //重装坦克
        Enemy enemy3 = new Enemy("重装坦克", 120, 120, 10, 20, true, 1,"防御姿态");
        //神秘法师
        Enemy enemy4 = new Enemy("神秘法师", 70, 70, 25, 8, true, 1.8,"火球术");
        list_enemy.add(enemy1);
        list_enemy.add(enemy2);
        list_enemy.add(enemy3);
        list_enemy.add(enemy4);
    }

    //正式开始游戏
    public void gameBegin(Character character, ArrayList<Enemy> list_enemy) {
        int numberCC = 1;//定义一个场次计数，用于计算当前是第几场战斗
        int numberHH = 1;//定义一个回合计数，用于计算当前是第几回合的战斗
        //场次循环
        while(character.getHP()>0){
            int random = Random(randomSkillEnemy);//获取随机敌人的索引
            ArrayList<Enemy> list_enemy_copy;//复制敌人集合给一个新集合，之后敌人战力增幅都放在新集合中
            list_enemy_copy = deepCopyList();
            if (numberCC >= 2) resetEnemyStats(random,list_enemy_copy,numberCC);
            System.out.println("═══════════════════════════════════════");
            System.out.println("⚔\uFE0F 第 " + numberCC + " 场战斗开始！对手: " + list_enemy_copy.get(random).getName());
            //回合循环
            HH(character,list_enemy_copy,random,numberHH);
            //战斗结束，场次加1
            numberCC++;
            //确定胜利后，胜场+1，恢复生命
            if(character.getHP()>0){
                //胜场+1
                CC_victory++;
                System.out.println("\uD83C\uDF89 你击败了 " + list_enemy_copy.get(random).getName() + "！");
                //战斗结束，角色恢复生命值
                int HP_remove = removeHP(character);
                System.out.println("\uD83D\uDC9A 战斗结束！你恢复了 " + HP_remove + " 点生命值");
                System.out.println("\uD83C\uDFC6 当前胜场: " + CC_victory);
            }else{
                //战斗结束--角色失败
                System.out.println("\uD83D\uDCA5 " + list_enemy_copy.get(random).getName() + " 击败了你！");
                System.out.println("\uD83C\uDFC6 当前胜场: " + CC_victory);
                CC_victory = 0;
                break;
            }
            //确定是否进行下一场
            System.out.println("═══════════════════════════════════════");
            if(nextCC()){}
            else{
                //输出当前角色血量
                printBlood(character);
                System.out.println("\uD83C\uDFC6 当前胜场: " + CC_victory);
                break;
            }
            //每胜利2场，角色进行属性提示
            characterPromove(character,CC_victory);
        }
    }

    //深拷贝集合
    public ArrayList<Enemy> deepCopyList() {
        ArrayList<Enemy> list_enemy_copy = new ArrayList<>();
        charEnemy(list_enemy_copy);
        return list_enemy_copy;
    }

    //重置敌人属性----重置的战力赋值到新的集合中，原来的集合只保留样本
    public void resetEnemyStats(int random, ArrayList<Enemy> list_enemy_copy,int numberCC) {//敌人在集合中的索引，要赋值的敌人新集合，场数
        //当前是第几场战斗，对敌人进行几倍强化
        //血量
        list_enemy_copy.get(random).setHP(list_enemy.get(random).getHP() + (numberCC - 1) * 10);
        //血量上限
        list_enemy_copy.get(random).setHPMax(list_enemy.get(random).getHPMax() + (numberCC - 1) * 10);
        //攻击
        list_enemy_copy.get(random).setATK(list_enemy.get(random).getATK() + (numberCC - 1) * 3);
        //防御
        list_enemy_copy.get(random).setDEF(list_enemy.get(random).getDEF() + (numberCC - 1) * 2);
    }

    //输出当前角色血量信息
    public void printBlood(Character character){
        //计算血量信息，输出多少个血量格子
        int numberBlood = (int) ((((double)(character.getHP()))/((double)(character.getMaxHP()))) * 10);//格子数
        System.out.print(character.getCharacterName() + ":[");
        for (int i = 1; i <= 10; i++) {
            if(i<=numberBlood){
                System.out.print("█");
            }else{
                System.out.print(" ");
            }
        }
        System.out.println("] " + character.getHP() + "/" + character.getMaxHP() + " HP");
    }
    //输出当前敌人血量信息
    public void printBloodEnemy(ArrayList<Enemy> list_enemy,int random){
        //计算血量信息，输出多少个血量格子
        int numberBlood = (int) ((((double)(list_enemy.get(random).getHP()))/((double)(list_enemy.get(random).getHPMax()))) * 10);//格子数
        System.out.print(list_enemy.get(random).getName() + ":[");
        for (int i = 1; i <= 10; i++) {
            if(i<=numberBlood){
                System.out.print("█");
            }else{
                System.out.print(" ");
            }
        }
        System.out.println("] " + list_enemy.get(random).getHP() + "/" + list_enemy.get(random).getHPMax() + " HP");
    }

    //选择技能+文本
    public int chooseSkillNext(Character character,String name,ArrayList<Enemy> list_enemy_copy,int random){
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("选择行动 (1-3): ");
        return chooseSkill(character,name,list_enemy_copy,random);
    }
    //选择技能/无文本
    public int chooseSkill(Character character,String name,ArrayList<Enemy> list_enemy_copy,int random){
        int choose = sc.nextInt();
        int skillATK = 0;//返回实际角色能造成的真实伤害
        int skillATK_DEF;//角色实际攻击伤害（减去了敌人防御后的伤害）

        //选择行动
        switch (choose) {
            case 1 :
                //返回能造成的伤害
                skillATK = character.getList_skill().get(0).skillEffects(character);
                skillATK_DEF = enemy_DEF(list_enemy_copy,random,skillATK);
                if(!list_enemy_copy.get(random).isState()){
                    skillATK_DEF = skillATK_DEF/2;
                }
                System.out.println("\uD83D\uDCA5 消耗" + character.getList_skill().get(0).getSkillCost() + "HP，你对 " + name + " 使用了普通攻击，造成 " + skillATK_DEF + " 点伤害！");
                break;

            case 2 :
                //返回能造成的伤害
                skillATK = character.getList_skill().get(1).skillEffects(character);
                skillATK_DEF = enemy_DEF(list_enemy_copy,random,skillATK);
                if(!list_enemy_copy.get(random).isState()){
                    skillATK_DEF = skillATK_DEF/2;
                }
                System.out.println("\uD83D\uDCA5 消耗" + character.getList_skill().get(1).getSkillCost() + "HP，你对 " + name + " 使用了强力一击，造成 " + skillATK_DEF + " 点伤害！");
                //使用技能消耗血量
                character.setHP(character.getHP() - character.getList_skill().get(1).getSkillCost());
                break;

            case 3 :
                int HP_linshi = character.getList_skill().get(1).RandomHP(Random(randomSkillHP));
                System.out.println("\uD83D\uDCA5 消耗" + character.getList_skill().get(2).getSkillCost() + "HP，你对 自己 使用了生命汲取，恢复 " + HP_linshi + " 点生命！");
                //使用技能消耗血量
                character.setHP(character.getHP() - character.getList_skill().get(2).getSkillCost());
                //使用技能恢复血量
                character.setHP(character.getHP() + HP_linshi);
                break;

            default:
                System.out.println("当前输入无效，请重新输入并选择行动(1-3): ");
                chooseSkill(character,name,list_enemy_copy,random);

        }
        return skillATK;
    }

    //对敌人造成伤害，并且敌人对角色进行攻击
    public void enemyHH(ArrayList<Enemy> list_enemy_copy,int random,int skillATK,Character character){//复制的敌人集合，敌人索引，对敌伤害,角色,场次
        //敌人接收伤害
        //对敌人的状态进行判断，如果是false，则伤害减半，并且状态变更为true
        if(!list_enemy_copy.get(random).isState()){
            list_enemy_copy.get(random).setHP(list_enemy_copy.get(random).getHP() - enemy_DEF(list_enemy_copy,random,skillATK)/2);
            list_enemy_copy.get(random).setState(true);
        }else{
            list_enemy_copy.get(random).setHP(list_enemy_copy.get(random).getHP() - enemy_DEF(list_enemy_copy,random,skillATK));
        }
        //判断敌人是否倒地死亡，若死亡则结束
        if(decisionDie(character,list_enemy_copy,random) == 2){
            //让直接结束，进行下一场
            return;
        }
        //敌人使用技能
        System.out.println("===== " + list_enemy_copy.get(random).getName() +"的回合 =====");
        //进行随机数判断，0则普通攻击（敌人当前攻击力），1则使用技能
        int random_enemy = Random(2);
        int enemySkillATK;
        String skill_enemy_name;
        if(random_enemy == 0){
            enemySkillATK = list_enemy_copy.get(random).getATK();
            skill_enemy_name = "普通攻击";
        }else{
            //判断是否为重装战士
            if(list_enemy_copy.get(random).getName().equals("重装坦克")){
                list_enemy_copy.get(random).setState(false);
                enemySkillATK = 0;
            }else{
                enemySkillATK = list_enemy_copy.get(random).skillEffects(list_enemy_copy.get(random));
            }
            skill_enemy_name = list_enemy_copy.get(random).getEnemySkillName();
        }
        enemySkillATK = character_DEF(character,enemySkillATK);
        System.out.println("⚔\uFE0F " + list_enemy_copy.get(random).getName() + " 对你使用了" + skill_enemy_name + "，造成 " + enemySkillATK + " 点伤害！");
        //对敌人造成的伤害进行结算
        character.setHP(character.getHP() - enemySkillATK);
        //判断角色血量是否小于等于0，是则结束游戏
        if(decisionDie(character,list_enemy_copy,random) == 1){
            //让直接结束，进行下一场
            return;
        }
    }

    //判断角色和敌人任意一方是否死亡
    public int decisionDie(Character character, ArrayList<Enemy> list_enemy_copy,int random){
        //角色死亡返回1，敌人死亡返回2，都未死亡返回0
        if (character.getHP()<=0){
            return 1;
        }else if(list_enemy_copy.get(random).getHP()<=0){
            return 2;
        }else{
            return 0;
        }
    }

    //回合循环
    public void HH(Character character,ArrayList<Enemy> list_enemy_copy,int random,int numberHH){
        //回合开始
        System.out.println("---------------------------------------");

        System.out.println("⚔\uFE0F 第 " + numberHH + " 回合开始！ ");
        //输出当前角色血量信息
        printBlood(character);
        //输出当前敌人血量信息
        printBloodEnemy(list_enemy_copy,random);
        //选择你的技能
        System.out.println("===== 你的回合 =====");
        //获取技能可以造成的伤害
        int skillATK = chooseSkillNext(character,list_enemy_copy.get(random).getName(),list_enemy_copy,random);
        //对敌人造成伤害，并且敌人对角色进行攻击
        enemyHH(list_enemy_copy,random,skillATK,character);
        //判断角色和敌人血量，以此来判断是否进行下一回合
        int numberpanduan = decisionDie(character,list_enemy_copy,random);
        if(numberpanduan == 0){
            numberHH++;
            HH(character,list_enemy_copy,random,numberHH);
        }
    }

    //战斗结束，角色恢复生命值
    public int removeHP(Character character){
        int HP_remove = r.nextInt(21) + 20;
        if((character.getHP() + HP_remove)>character.getMaxHP()){
            character.setHP(character.getMaxHP());
        }else{
            character.setHP(character.getHP()+HP_remove);
        }
        return HP_remove;
    }

    //判断是否进行下一场--true继续，false不继续
    public boolean nextCC(){
        System.out.println("继续下一场战斗？(y/n):");
        String next = sc.next();
        if(next.equals("y")){
            return true;
        }else if(next.equals("n")){
            return false;
        }else{
            System.out.println("您输入的字符有误，请重新输入");
            return nextCC();
        }
    }

    //敌人计算受到的伤害：伤害为能造成的伤害-防御力*1
    public int enemy_DEF(ArrayList<Enemy> list_enemy_copy,int random,int skillATK){
        if((skillATK - list_enemy_copy.get(random).getDEF())>0){
            return skillATK - list_enemy_copy.get(random).getDEF();
        }else{
            return 0;
        }
    }
    //角色计算受到伤害：伤害为能造成的伤害-防御力*1
    public int character_DEF(Character character, int enemySkillATK){
        if((enemySkillATK - character.getDEF())>0){
            return enemySkillATK - character.getDEF();
        }else{
            return 0;
        }
    }

    //每胜利2场，角色进行属性提升
    public void characterPromove(Character character,int CC_victory){
        if(CC_victory%2 == 0){
            character.setMaxHP(character.getMaxHP() + 30);
            character.setHP(character.getHP() + 30);
            character.setATK(character.getATK() + 5);
            character.setDEF(character.getDEF() + 3);
        }
    }
}
