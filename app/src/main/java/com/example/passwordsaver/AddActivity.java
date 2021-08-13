package com.example.passwordsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class AddActivity extends AppCompatActivity {

    EditText name_input, username_input, password_input;
    Button add_button;

    ImageButton imageButton;
    static boolean ul,uu,ud,us,uc,show;
    static String sug="";
    TextView textView1,textView21,textView22,textView23,textView31,textView32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        add_button = findViewById(R.id.add_button);
        //button2 = findViewById(R.id.button2);
        imageButton = findViewById(R.id.imageButton);
        textView1=findViewById(R.id.textView1);
        textView21=findViewById(R.id.textView21);
        textView22=findViewById(R.id.textView22);
        textView23=findViewById(R.id.textView23);
        textView31=findViewById(R.id.textView31);
        textView32=findViewById(R.id.textView32);

        password_input.setTransformationMethod(new PasswordTransformationMethod());

        textView1.setText("");
        textView21.setVisibility(View.INVISIBLE);
        textView22.setVisibility(View.INVISIBLE);
        textView23.setVisibility(View.INVISIBLE);
        textView31.setVisibility(View.INVISIBLE);
        textView32.setText("");

        password_input.setTransformationMethod(new PasswordTransformationMethod());

        password_input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                sug="";
                textView31.setText("Suggestions:");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0) {
                    textView1.setText("");
                    textView21.setVisibility(View.INVISIBLE);
                    textView22.setVisibility(View.INVISIBLE);
                    textView23.setVisibility(View.INVISIBLE);
                    textView31.setVisibility(View.INVISIBLE);
                    textView32.setText("");
                } else  {
                    textView21.setVisibility(View.VISIBLE);
                    textView22.setVisibility(View.VISIBLE);
                    textView23.setVisibility(View.VISIBLE);
                    textView31.setVisibility(View.VISIBLE);
                    boolean stageOne = gfg(s.toString());
                    boolean stageTwo = mine(s.toString());
                    boolean stageThree = github(s.toString());
                    textView32.setText(sug);
                    if(sug.length()==0) {
                        textView31.setVisibility(View.INVISIBLE);
                        sug="";
                    }
                    if (stageOne && stageTwo && stageThree) {
                        textView1.setText("strong");
                        textView1.setTextColor(Color.GREEN);
                    } else if ((stageOne && stageTwo) || (stageTwo && stageThree) || (stageThree && stageOne)) {
                        textView1.setText("good");
                        textView1.setTextColor(Color.BLUE);
                    } else if((stageOne || stageTwo || stageThree) && s.length()>=8) {
                        textView1.setText("fair");
                        textView1.setTextColor(Color.YELLOW);
                    } else {
                        textView1.setText("weak");
                        textView1.setTextColor(Color.RED);
                    }
                    if(s.length()>3) {
                        textView22.setText(hive(s.toString()));
                    } else {
                        textView22.setText("Your password is too short!");
                        textView21.setVisibility(View.GONE);
                        textView23.setVisibility(View.GONE);
                    }
                }
            }

        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addPassword(name_input.getText().toString().trim(),
                        username_input.getText().toString().trim(),
                        password_input.getText().toString().trim());

                //Refresh Activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

/*        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=geekPassword(10);
                while (!mineCheck(result)) {
                    result=geekPassword(10);
                }
                password_input.setText(result);
            }
        });*/

    }

    static String geekPassword(int len) {
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "`~!@#$%^&*()_+-={}|[]\\:;<>?,./'\"";
        Random random_method = new Random();
        Set<Character> set = new LinkedHashSet<>();
        for (int i = 1; i <= 100; i++) {
            int ran=random_method.nextInt(4)+1;
            switch (ran) {
                case 1:set.add(Capital_chars.charAt(random_method.nextInt(Capital_chars.length())));
                    break;
                case 2:set.add(Small_chars.charAt(random_method.nextInt(Small_chars.length())));
                    break;
                case 3:set.add(numbers.charAt(random_method.nextInt(numbers.length())));
                    break;
                case 4:set.add(symbols.charAt(random_method.nextInt(symbols.length())));
                    break;
            }
        }
        StringBuilder ans= new StringBuilder();
        for(char i:set) {
            ans.append(i);
        }
        ans = new StringBuilder(ans.substring(0, len));

        return ans.toString();
    }

    public static boolean mineCheck(String in) {
        Set<Character> set1=new HashSet<>();
        for(int i=0;i<in.length();i++) {
            set1.add(in.charAt(i));
        }
        StringBuilder input1= new StringBuilder();
        for(Character c:set1) {
            input1.append(c);
        }
        String input=new String(input1);
        int n = input.length();
        boolean hasLower = false, hasUpper = false, hasDigit = false, specialChar = false;
        Set<Character> set = new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));
        for (char i : input.toCharArray()) {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }
        return hasDigit && hasLower && hasUpper && specialChar && (n >= 8);
    }

    public void showHide(View view) {
        if(show) {
            password_input.setTransformationMethod(new PasswordTransformationMethod());
            imageButton.setImageResource(R.drawable.hide);
            show=false;
        } else {
            password_input.setTransformationMethod(null);
            imageButton.setImageResource(R.drawable.show);
            show=true;
        }
    }

    public void randomGenerate(View view) {
        String result=geekPassword(10);
        while (!mineCheck(result)) {
            result=geekPassword(10);
        }
        password_input.setText(result);
    }

    public static boolean gfg(String input) {
        int n = input.length();
        boolean hasLower = false, hasUpper = false, hasDigit = false, specialChar = false;
        Set<Character> set = new HashSet<>(Arrays.asList('~','`','!', '@', '#', '$', '%', '^', '&', '*', '(', ')','-', '+','_','=','[',']','{','}',':',';','\'','"','<','>',',','.','?','/','.','|','\\',' '));
        for (char i : input.toCharArray()) {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }
        if(!hasLower&&!ul) {
            sug+="Add Lowercase letters!\n";
        }
        if(!hasDigit&&!ud) {
            sug+="Add Numeric digits!\n";
        }
        if(!hasUpper&&!uu) {
            sug+="Add Uppercase letters!\n";
        }
        if(!specialChar&&!us) {
            sug+="Add Special characters!\n";
        }
        if(n<8&&!uc) {
            sug+="Add Characters!\n";
        }
        return hasDigit && hasLower && hasUpper && specialChar && (n >= 8);
    }

    public static boolean mine(String in) {
        Set<Character> set1=new HashSet<>();
        for(int i=0;i<in.length();i++) {
            set1.add(in.charAt(i));
        }
        StringBuilder input1= new StringBuilder();
        for(Character c:set1) {
            input1.append(c);
        }
        String input=new String(input1);
        int n = input.length();
        boolean hasLower = false, hasUpper = false, hasDigit = false, specialChar = false;
        Set<Character> set = new HashSet<>(Arrays.asList('~','`','!', '@', '#', '$', '%', '^', '&', '*', '(', ')','-', '+','_','=','[',']','{','}',':',';','\'','"','<','>',',','.','?','/','.','|','\\',' '));
        for (char i : input.toCharArray()) {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }
        if(!hasLower) {
            sug+="Add Unique Lowercase letters!\n";
            ul=true;
        }
        if(!hasDigit) {
            sug+="Add Unique Numeric digits!\n";
            ud=true;
        }
        if(!hasUpper) {
            sug+="Add Unique Uppercase letters!\n";
            uu=true;
        }
        if(!specialChar) {
            sug+="Add Unique Special characters!\n";
            us=true;
        }
        if(n<8) {
            sug+="Add Unique Characters!\n";
            uc=true;
        }
        return hasDigit && hasLower && hasUpper && specialChar && (n >= 8);
    }

    public static boolean github(String input) {
        String mostCommonPasswords="123456,password,12345678,qwerty,123456789,12345,1234,111111,1234567,dragon,123123,baseball,abc123,football,monkey,letmein,696969,shadow,master,666666,qwertyuiop,123321,mustang,1234567890,michael,654321,pussy,superman,1qaz2wsx,7777777,fuckyou,121212,000000,qazwsx,123qwe,killer,trustno1,jordan,jennifer,zxcvbnm,asdfgh,hunter,buster,soccer,harley,batman,andrew,tigger,sunshine,iloveyou,fuckme,2000,charlie,robert,thomas,hockey,ranger,daniel,starwars,klaster,112233,george,asshole,computer,michelle,jessica,pepper,1111,zxcvbn,555555,11111111,131313,freedom,777777,pass,fuck,maggie,159753,aaaaaa,ginger,princess,joshua,cheese,amanda,summer,love,ashley,6969,nicole,chelsea,biteme,matthew,access,yankees,987654321,dallas,austin,thunder,taylor,matrix,william,corvette,hello,martin,heather,secret,fucker,merlin,diamond,1234qwer,gfhjkm,hammer,silver,222222,88888888,anthony,justin,test,bailey,q1w2e3r4t5,patrick,internet,scooter,orange,11111,golfer,cookie,richard,samantha,bigdog,guitar,jackson,whatever,mickey,chicken,sparky,snoopy,maverick,phoenix,camaro,sexy,peanut,morgan,welcome,falcon,cowboy,ferrari,samsung,andrea,smokey,steelers,joseph,mercedes,dakota,arsenal,eagles,melissa,boomer,booboo,spider,nascar,monster,tigers,yellow,xxxxxx,123123123,gateway,marina,diablo,bulldog,qwer1234,compaq,purple,hardcore,banana,junior,hannah,123654,porsche,lakers,iceman,money,cowboys,987654,london,tennis,999999,ncc1701,coffee,scooby,0000,miller,boston,q1w2e3r4,fuckoff,brandon,yamaha,chester,mother,forever,johnny,edward,333333,oliver,redsox,player,nikita,knight,fender,barney,midnight,please,brandy,chicago,badboy,iwantu,slayer,rangers,charles,angel,flower,bigdaddy,rabbit,wizard,bigdick,jasper,enter,rachel,chris,steven,winner,adidas,victoria,natasha,1q2w3e4r,jasmine,winter,prince,panties,marine,ghbdtn,fishing,cocacola,casper,james,232323,raiders,888888,marlboro,gandalf,asdfasdf,crystal,87654321,12344321,sexsex,golden,blowme,bigtits,8675309,panther,lauren,angela,bitch,spanky,thx1138,angels,madison,winston,shannon,mike,toyota,blowjob,jordan23,canada,sophie,Password,apples,dick,tiger,razz,123abc,pokemon,qazxsw,55555,qwaszx,muffin,johnson,murphy,cooper,jonathan,liverpoo,david,danielle,159357,jackie,1990,123456a,789456,turtle,horny,abcd1234,scorpion,qazwsxedc,101010,butter,carlos,password1,dennis,slipknot,qwerty123,booger,asdf,1991,black,startrek,12341234,cameron,newyork,rainbow,nathan,john,1992,rocket,viking,redskins,butthead,asdfghjkl,1212,sierra,peaches,gemini,doctor,wilson,sandra,helpme,qwertyui,victor,florida,dolphin,pookie,captain,tucker,blue,liverpool,theman,bandit,dolphins,maddog,packers,jaguar,lovers,nicholas,united,tiffany,maxwell,zzzzzz,nirvana,jeremy,suckit,stupid,porn,monica,elephant,giants,jackass,hotdog,rosebud,success,debbie,mountain,444444,xxxxxxxx,warrior,1q2w3e4r5t,q1w2e3,123456q,albert,metallic,lucky,azerty,7777,shithead,alex,bond007,alexis,1111111,samson,5150,willie,scorpio,bonnie,gators,benjamin,voodoo,driver,dexter,2112,jason,calvin,freddy,212121,creative,12345a,sydney,rush2112,1989,asdfghjk,red123,bubba,4815162342,passw0rd,trouble,gunner,happy,fucking,gordon,legend,jessie,stella,qwert,eminem,arthur,apple,nissan,bullshit,bear,america,1qazxsw2,nothing,parker,4444,rebecca,qweqwe,garfield,01012011,beavis,69696969,jack,asdasd,december,2222,102030,252525,11223344,magic,apollo,skippy,315475,girls,kitten,golf,copper,braves,shelby,godzilla,beaver,fred,tomcat,august,buddy,airborne,1993,1988,lifehack,qqqqqq,brooklyn,animal,platinum,phantom,online,xavier,darkness,blink182,power,fish,green,789456123,voyager,police,travis,12qwaszx,heaven,snowball,lover,abcdef,00000,pakistan,007007,walter,playboy,blazer,cricket,sniper,hooters,donkey,willow,loveme,saturn,therock,redwings,bigboy,pumpkin,trinity,williams,tits,nintendo,digital,destiny,topgun,runner,marvin,guinness,chance,bubbles,testing,fire,november,minecraft,asdf1234,lasvegas,sergey,broncos,cartman,private,celtic,birdie,little,cassie,babygirl,donald,beatles,1313,dickhead,family,12121212,school,louise,gabriel,eclipse,fluffy,147258369,lol123,explorer,beer,nelson,flyers,spencer,scott,lovely,gibson,doggie,cherry,andrey,snickers,buffalo,pantera,metallica,member,carter,qwertyu,peter,alexande,steve,bronco,paradise,goober,5555,samuel,montana,mexico,dreams,michigan,cock,carolina,yankee,friends,magnum,surfer,poopoo,maximus,genius,cool,vampire,lacrosse,asd123,aaaa,christin,kimberly,speedy,sharon,carmen,111222,kristina,sammy,racing,ou812,sabrina,horses,0987654321,qwerty1,pimpin,baby,stalker,enigma,147147,star,poohbear,boobies,147258,simple,bollocks,12345q,marcus,brian,1987,qweasdzxc,drowssap,hahaha,caroline,barbara,dave,viper,drummer,action,einstein,bitches,genesis,hello1,scotty,friend,forest,010203,hotrod,google,vanessa,spitfire,badger,maryjane,friday,alaska,1232323q,tester,jester,jake,champion,billy,147852,rock,hawaii,badass,chevy,420420,walker,stephen,eagle1,bill,1986,october,gregory,svetlana,pamela,1984,music,shorty,westside,stanley,diesel,courtney,242424,kevin,porno,hitman,boobs,mark,12345qwert,reddog,frank,qwe123,popcorn,patricia,aaaaaaaa,1969,teresa,mozart,buddha,anderson,paul,melanie,abcdefg,security,lucky1,lizard,denise,3333,a12345,123789,ruslan,stargate,simpsons,scarface,eagle,123456789a,thumper,olivia,naruto,1234554321,general,cherokee,a123456,vincent,Usuckballz1,spooky,qweasd,cumshot,free,frankie,douglas,death,1980,loveyou,kitty,kelly,veronica,suzuki,semperfi,penguin,mercury,liberty,spirit,scotland,natalie,marley,vikings,system,sucker,king,allison,marshall,1979,098765,qwerty12,hummer,adrian,1985,vfhbyf,sandman,rocky,leslie,antonio,98765432,4321,softball,passion,mnbvcxz,bastard,passport,horney,rascal,howard,franklin,bigred,assman,alexander,homer,redrum,jupiter,claudia,55555555,141414,zaq12wsx,shit,patches,nigger,cunt,raider,infinity,andre,54321,galore,college,russia,kawasaki,bishop,77777777,vladimir,money1,freeuser,wildcats,francis,disney,budlight,brittany,1994,00000000,sweet,oksana,honda,domino,bulldogs,brutus,swordfis,norman,monday,jimmy,ironman,ford,fantasy,9999,7654321,PASSWORD,hentai,duncan,cougar,1977,jeffrey,house,dancer,brooke,timothy,super,marines,justice,digger,connor,patriots,karina,202020,molly,everton,tinker,alicia,rasdzv3,poop,pearljam,stinky,naughty,colorado,123123a,water,test123,ncc1701d,motorola,ireland,asdfg,slut,matt,houston,boogie,zombie,accord,vision,bradley,reggie,kermit,froggy,ducati,avalon,6666,9379992,sarah,saints,logitech,chopper,852456,simpson,madonna,juventus,claire,159951,zachary,yfnfif,wolverin,warcraft,hello123,extreme,penis,peekaboo,fireman,eugene,brenda,123654789,russell,panthers,georgia,smith,skyline,jesus,elizabet,spiderma,smooth,pirate,empire,bullet,8888,virginia,valentin,psycho,predator,arizona,134679,mitchell,alyssa,vegeta,titanic,christ,goblue,fylhtq,wolf,mmmmmm,kirill,indian,hiphop,baxter,awesome,people,danger,roland,mookie,741852963,1111111111,dreamer,bambam,arnold,1981,skipper,serega,rolltide,elvis,changeme,simon,1q2w3e,lovelove,fktrcfylh,denver,tommy,mine,loverboy,hobbes,happy1,alison,nemesis,chevelle,cardinal,burton,wanker,picard,151515,tweety,michael1,147852369,12312,xxxx,windows,turkey,456789,1974,vfrcbv,sublime,1975,galina,bobby,newport,manutd,daddy,american,alexandr,1966,victory,rooster,qqq111,madmax,electric,bigcock,a1b2c3,wolfpack,spring,phpbb,lalala,suckme,spiderman,eric,darkside,classic,raptor,123456789q,hendrix,1982,wombat,avatar,alpha,zxc123,crazy,hard,england,brazil,1978,01011980,wildcat,polina,freepass,carrie,99999999,qaz123,holiday,fyfcnfcbz,brother,taurus,shaggy,raymond,maksim,gundam,admin,vagina,pretty,pickle,good,chronic,alabama,airplane,22222222,1976,1029384756,01011,time,sports,ronaldo,pandora,cheyenne,caesar,billybob,bigman,1968,124578,snowman,lawrence,kenneth,horse,france,bondage,perfect,kristen,devils,alpha1,pussycat,kodiak,flowers,1973,01012000,leather,amber,gracie,chocolat,bubba1,catch22,business,2323,1983,cjkysirj,1972,123qweasd,ytrewq,wolves,stingray,ssssss,serenity,ronald,greenday,135790,010101,tiger1,sunset,charlie1,berlin,bbbbbb,171717,panzer,lincoln,katana,firebird,blizzard,a1b2c3d4,white,sterling,redhead,password123,candy,anna,142536,sasha,pyramid,outlaw,hercules,garcia,454545,trevor,teens,maria,kramer,girl,popeye,pontiac,hardon,dude,aaaaa,323232,tarheels,honey,cobra,buddy1,remember,lickme,detroit,clinton,basketball,zeppelin,whynot,swimming,strike,service,pavilion,michele,engineer,dodgers,britney,bobafett,adam,741852,21122112,xxxxx,robbie,miranda,456123,future,darkstar,icecream,connie,1970,jones,hellfire,fisher,fireball,apache,fuckit,blonde,bigmac,abcd,morris,angel1,666999,321321,simone,rockstar,flash,defender,1967,wallace,trooper,oscar,norton,casino,cancer,beauty,weasel,savage,raven,harvey,bowling,246810,wutang,theone,swordfish,stewart,airforce,abcdefgh,nipples,nastya,jenny,hacker,753951,amateur,viktor,srinivas,maxima,lennon,freddie,bluebird,qazqaz,presario,pimp,packard,mouse,looking,lesbian,jeff,cheryl,2001,wrangler,sandy,machine,lights,eatme,control,tattoo,precious,harrison,duke,beach,tornado,tanner,goldfish,catfish,openup,manager,1971,street,Soso123aljg,roscoe,paris,natali,light,julian,jerry,dilbert,dbrnjhbz,chris1,atlanta,xfiles,thailand,sailor,pussies,pervert,lucifer,longhorn,enjoy,dragons,young,target,elaine,dustin,123qweasdzxc,student,madman,lisa,integra,wordpass,prelude,newton,lolita,ladies,hawkeye,corona,bubble,31415926,trigger,spike,katie,iloveu,herman,design,cannon,999999999,video,stealth,shooter,nfnmzyf,hottie,browns,314159,trucks,malibu,bruins,bobcat,barbie,1964,orlando,letmein1,freaky,foobar,cthutq,baller,unicorn,scully,pussy1,potter,cookies,pppppp,philip,gogogo,elena,country,assassin,1010,zaqwsx,testtest,peewee,moose,microsoft,teacher,sweety,stefan,stacey,shotgun,random,laura,hooker,dfvgbh,devildog,chipper,athena,winnie,valentina,pegasus,kristin,fetish,butterfly,woody,swinger,seattle,lonewolf,joker,booty,babydoll,atlantis,tony,powers,polaris,montreal,angelina,77777,tickle,regina,pepsi,gizmo,express,dollar,squirt,shamrock,knicks,hotstuff,balls,transam,stinger,smiley,ryan,redneck,mistress,hjvfirf,cessna,bunny,toshiba,single,piglet,fucked,father,deftones,coyote,castle,cadillac,blaster,valerie,samurai,oicu812,lindsay,jasmin,james1,ficken,blahblah,birthday,1234abcd,01011990,sunday,manson,flipper,asdfghj,181818,wicked,great,daisy,babes,skeeter,reaper,maddie,cavalier,veronika,trucker,qazwsx123,mustang1,goldberg,escort,12345678910,wolfgang,rocks,mylove,mememe,lancer,ibanez,travel,sugar,snake,sister,siemens,savannah,minnie,leonardo,basketba,1963,trumpet,texas,rocky1,galaxy,cristina,aardvark,shelly,hotsex,goldie,fatboy,benson,321654,141627,sweetpea,ronnie,indigo,13131313,spartan,roberto,hesoyam,freeman,freedom1,fredfred,pizza,manchester,lestat,kathleen,hamilton,erotic,blabla,22222,1995,skater,pencil,passwor,larisa,hornet,hamlet,gambit,fuckyou2,alfred,456456,sweetie,marino,lollol,565656,techno,special,renegade,insane,indiana,farmer,drpepper,blondie,bigboobs,272727,1a2b3c,valera,storm,seven,rose,nick,mister,karate,casey,1qaz2wsx3edc,1478963,maiden,julie,curtis,colors,christia,buckeyes,13579,0123456789,toronto,stephani,pioneer,kissme,jungle,jerome,holland,harry,garden,enterpri,dragon1,diamonds,chrissy,bigone,343434,wonder,wetpussy,subaru,smitty,racecar,pascal,morpheus,joanne,irina,indians,impala,hamster,charger,change,bigfoot,babylon,66666666,timber,redman,pornstar,bernie,tomtom,thuglife,millie,buckeye,aaron,virgin,tristan,stormy,rusty,pierre,napoleon,monkey1,highland,chiefs,chandler,catdog,aurora,1965,trfnthbyf,sampson,nipple,dudley,cream,consumer,burger,brandi,welcome1,triumph,joejoe,hunting,dirty,caserta,brown,aragorn,363636,mariah,element,chichi,2121,123qwe123,wrinkle1,smoke,omega,monika,leonard,justme,hobbit,gloria,doggy,chicks,bass,audrey,951753,51505150,11235813,sakura,philips,griffin,butterfl,artist,66666,island,goforit,emerald,elizabeth,anakin,watson,poison,none,italia,callie,bobbob,autumn,andreas,123,sherlock,q12345,pitbull,marathon,kelsey,inside,german,blackie,access14,123asd,zipper,overlord,nadine,marie,basket,trombone,stones,sammie,nugget,naked,kaiser,isabelle,huskers,bomber,barcelona,babylon5,babe,alpine,weed,ultimate,pebbles,nicolas,marion,loser,linda,eddie,wesley,warlock,tyler,goddess,fatcat,energy,david1,bassman,yankees1,whore,trojan,trixie,superfly,kkkkkk,ybrbnf,warren,sophia,sidney,pussys,nicola,campbell,vfvjxrf,singer,shirley,qawsed,paladin,martha,karen,help,harold,geronimo,forget,concrete,191919,westham,soldier,q1w2e3r4t5y6,poiuyt,nikki,mario,juice,jessica1,global,dodger,123454321,webster,titans,tintin,tarzan,sexual,sammy1,portugal,onelove,marcel,manuel,madness,jjjjjj,holly,christy,424242,yvonne,sundance,sex4me,pleasure,logan,danny,wwwwww,truck,spartak,smile,michel,history,Exigen,65432,1234321,sherry,sherman,seminole,rommel,network,ladybug,isabella,holden,harris,germany,fktrctq,cotton,angelo,14789632,sergio,qazxswedc,moon,jesus1,trunks,snakes,sluts,kingkong,bluesky,archie,adgjmptw,911911,112358,sunny,suck,snatch,planet,panama,ncc1701e,mongoose,head,hansolo,desire,alejandr,1123581321,whiskey,waters,teen,party,martina,margaret,january,connect,bluemoon,bianca,andrei,5555555,smiles,nolimit,long,assass,abigail,555666,yomama,rocker,plastic,katrina,ghbdtnbr,ferret,emily,bonehead,blessed,beagle,asasas,abgrtyu,sticky,olga,japan,jamaica,home,hector,dddddd,1961,turbo,stallion,personal,peace,movie,morrison,joanna,geheim,finger,cactus,7895123,susan,super123,spyder,mission,anything,aleksandr,zxcvb,shalom,rhbcnbyf,pickles,passat,natalia,moomoo,jumper,inferno,dietcoke,cumming,cooldude,chuck,christop,million,lollipop,fernando,christian,blue22,bernard,apple1,unreal,spunky,ripper,open,niners,letmein2,flatron,faster,deedee,bertha,april,4128,01012010,werewolf,rubber,punkrock,orion,mulder,missy,larry,giovanni,gggggg,cdtnkfyf,yoyoyo,tottenha,shaved,newman,lindsey,joey,hongkong,freak,daniela,camera,brianna,blackcat,a1234567,1q1q1q,zzzzzzzz,stars,pentium,patton,jamie,hollywoo,florence,biscuit,beetle,andy,always,speed,sailing,phillip,legion,gn56gn56,909090,martini,dream,darren,clifford,2002,stocking,solomon,silvia,pirates,office,monitor,monique,milton,matthew1,maniac,loulou,jackoff,immortal,fossil,dodge,delta,44444444,121314,sylvia,sprite,shadow1,salmon,diana,shasta,patriot,palmer,oxford,nylons,molly1,irish,holmes,curious,asdzxc,1999,makaveli,kiki,kennedy,groovy,foster,drizzt,twister,snapper,sebastia,philly,pacific,jersey,ilovesex,dominic,charlott,carrot,anthony1,africa,111222333,sharks,serena,satan666,maxmax,maurice,jacob,gerald,cosmos,columbia,colleen,cjkywt,cantona,brooks,99999,787878,rodney,nasty,keeper,infantry,frog,french,eternity,dillon,coolio,condor,anton,waterloo,velvet,vanhalen,teddy,skywalke,sheila,sesame,seinfeld,funtime,012345,standard,squirrel,qazwsxed,ninja,kingdom,grendel,ghost,fuckfuck,damien,crimson,boeing,bird,biggie,090909,zaq123,wolverine,wolfman,trains,sweets,sunrise,maxine,legolas,jericho,isabel,foxtrot,anal,shogun,search,robinson,rfrfirf,ravens,privet,penny,musicman,memphis,megadeth,dogs,butt,brownie,oldman,graham,grace,505050,verbatim,support,safety,review,newlife,muscle,herbert,colt45,bottom,2525,1q2w3e4r5t6y,1960,159159,western,twilight,thanks,suzanne,potato,pikachu,murray,master1,marlin,gilbert,getsome,fuckyou1,dima,denis,789789,456852,stone,stardust,seven7,peanuts,obiwan,mollie,licker,kansas,frosty,ball,262626,tarheel,showtime,roman,markus,maestro,lobster,darwin,cindy,chubby,2468,147896325,tanker,surfing,skittles,showme,shaney14,qwerty12345,magic1,goblin,fusion,blades,banshee,alberto,123321123,123098,powder,malcolm,intrepid,garrett,delete,chaos,bruno,1701,tequila,short,sandiego,python,punisher,newpass,iverson,clayton,amadeus,1234567a,stimpy,sooners,preston,poopie,photos,neptune,mirage,harmony,gold,fighter,dingdong,cats,whitney,sucks,slick,rick,ricardo,princes,liquid,helena,daytona,clover,blues,anubis,1996,192837465,starcraft,roxanne,pepsi1,mushroom,eatshit,dagger,cracker,capital,brendan,blackdog,25802580,strider,slapshot,porter,pink,jason1,hershey,gothic,flight,ekaterina,cody,buffy,boss,bananas,aaaaaaa,123698745,1234512345,tracey,miami,kolobok,danni,chargers,cccccc,blue123,bigguy,33333333,0.0.000,warriors,walnut,raistlin,ping,miguel,latino,griffey,green1,gangster,felix,engine,doodle,coltrane,byteme,buck,asdf123,123456z,0007,vertigo,tacobell,shark,portland,penelope,osiris,nymets,nookie,mary,lucky7,lucas,lester,ledzep,gorilla,coco,bugger,bruce,blood,bentley,battle,1a2b3c4d,19841984,12369874,weezer,turner,thegame,stranger,sally,Mailcreated5240,knights,halflife,ffffff,dorothy,dookie,damian,258456,women,trance,qwerasdf,playtime,paradox,monroe,kangaroo,henry,dumbass,dublin,charly,butler,brasil,blade,blackman,bender,baggins,wisdom,tazman,swallow,stuart,scruffy,phoebe,panasonic,Michael,masters,ghjcnj,firefly,derrick,christine,beautiful,auburn,archer,aliens,161616,1122,woody1,wheels,test1,spanking,robin,redred,racerx,postal,parrot,nimrod,meridian,madrid,lonestar,kittycat,hell,goodluck,gangsta,formula,devil,cassidy,camille,buttons,bonjour,bingo,barcelon,allen,98765,898989,303030,2020,0000000,tttttt,tamara,scoobydo,samsam,rjntyjr,richie,qwertz,megaman,luther,jazz,crusader,bollox,123qaz,12312312,102938,window,sprint,sinner,sadie,rulez,quality,pooper,pass123,oakland,misty,lvbnhbq,lady,hannibal,guardian,grizzly,fuckface,finish,discover,collins,catalina,carson,black1,bang,annie,123987,1122334455,wookie,volume,tina,rockon,qwer,molson,marco,californ,angelica,2424,world,william1,stonecol,shemale,shazam,picasso,oracle,moscow,luke,lorenzo,kitkat,johnjohn,janice,gerard,flames,duck,dark,celica,445566,234567,yourmom,topper,stevie,septembe,scarlett,santiago,milano,lowrider,loving,incubus,dogdog,anastasia,1962,123zxc,vacation,tempest,sithlord,scarlet,rebels,ragnarok,prodigy,mobile,keyboard,golfing,english,carlo,anime,545454,19921992,11112222,vfhecz,sobaka,shiloh,penguins,nuttertools,mystery,lorraine,llllll,lawyer,kiss,jeep,gizmodo,elwood,dkflbvbh,987456,6751520,12121,titleist,tardis,tacoma,smoker,shaman,rootbeer,magnolia,julia,juan,hoover,gotcha,dodgeram,creampie,buffett,bridge,aspirine,456654,socrates,photo,parola,nopass,megan,lucy,kenwood,kenny,imagine,forgot,cynthia,blondes,ashton,aezakmi,1234567q,viper1,terry,sabine,redalert,qqqqqqqq,munchkin,monkeys,mersedes,melvin,mallard,lizzie,imperial,honda1,gremlin,gillian,elliott,defiant,dadada,cooler,bond,blueeyes,birdman,bigballs,analsex,753159,zaq1xsw2,xanadu,weather,violet,sergei,sebastian,romeo,research,putter,oooooo,national,lexmark,hotboy,greg,garbage,colombia,chucky,carpet,bobo,bobbie,assfuck,88888,01012001,smokin,shaolin,roger,rammstein,pussy69,katerina,hearts,frogger,freckles,dogg,dixie,claude,caliente,amazon,abcde,1221,wright,willis,spidey,sleepy,sirius,santos,rrrrrr,randy,picture,payton,mason,dusty,director,celeste,broken,trebor,sheena,qazwsxedcrfv,polo,oblivion,mustangs,margarita,letsgo,josh,jimbob,jimbo,janine,jackal,iforgot,hallo,fatass,deadhead,abc12,zxcv1234,willy,stud,slappy,roberts,rescue,porkchop,noodles,nellie,mypass,mikey,marvel,laurie,grateful,fuck_inside,formula1,Dragon,cxfcnmt,bridget,aussie,asterix,a1s2d3f4,23232323,123321q,veritas,spankme,shopping,roller,rogers,queen,peterpan,palace,melinda,martinez,lonely,kristi,justdoit,goodtime,frances,camel,beckham,atomic,alexandra,active,223344,vanilla,thankyou,springer,sommer,Software,sapphire,richmond,printer,ohyeah,massive,lemons,kingston,granny,funfun,evelyn,donnie,deanna,brucelee,bosco,aggies,313131,wayne,thunder1,throat,temple,smudge,qqqq,qawsedrf,plymouth,pacman,myself,mariners,israel,hitler,heather1,faith,Exigent,clancy,chelsea1,353535,282828,123456qwerty,tobias,tatyana,stuff,spectrum,sooner,shitty,sasha1,pooh,pineappl,mandy,labrador,kisses,katrin,kasper,kaktus,harder,eduard,dylan,dead,chloe,astros,1234567890q,10101010,stephanie,satan,hudson,commando,bones,bangkok,amsterdam,1959,webmaster,valley,space,southern,rusty1,punkin,napass,marian,magnus,lesbians,krishna,hungry,hhhhhh,fuckers,fletcher,content,account,906090,thompson,simba,scream,q1q1q1,primus,Passw0rd,mature,ivanov,husker,homerun,esther,ernest,champs,celtics,candyman,bush,boner,asian,aquarius,33333,zxcv,starfish,pics,peugeot,painter,monopoly,lick,infiniti,goodbye,gangbang,fatman,darling,celine,camelot,boat,blackjac,barkley,area51,8J4yE3Uz,789654,19871987,0000000000,vader,shelley,scrappy,sarah1,sailboat,richard1,moloko,method,mama,kyle,kicker,keith,judith,john316,horndog,godsmack,flyboy,emmanuel,drago,cosworth,blake,19891989,writer,usa123,topdog,timmy,speaker,rosemary,pancho,night,melody,lightnin,life,hidden,gator,farside,falcons,desert,chevrole,catherin,carolyn,bowler,anders,666777,369369,yesyes,sabbath,qwerty123456,power1,pete,oscar1,ludwig,jammer,frontier,fallen,dance,bryan,asshole1,amber1,aaa111,123457,01011991,terror,telefon,strong,spartans,sara,odessa,luckydog,frank1,elijah,chang,center,bull,blacks,15426378,132435,vivian,tanya,swingers,stick,snuggles,sanchez,redbull,reality,qwertyuio,qwert123,mandingo,ihateyou,hayden,goose,franco,forrest,double,carol,bohica,bell,beefcake,beatrice,avenger,andrew1,anarchy,963852,1366613,111111111,whocares,scooter1,rbhbkk,matilda,labtec,kevin1,jojo,jesse,hermes,fitness,doberman,dawg,clitoris,camels,5555555555,1957,vulcan,vectra,topcat,theking,skiing,nokia,muppet,moocow,leopard,kelley,ivan,grover,gjkbyf,filter,elvis1,delta1,dannyboy,conrad,children,catcat,bossman,bacon,amelia,alice,2222222,viktoria,valhalla,tricky,terminator,soccer1,ramona,puppy,popopo,oklahoma,ncc1701a,mystic,loveit,looker,latin,laptop,laguna,keystone,iguana,herbie,cupcake,clarence,bunghole,blacky,bennett,bart,19751975,12332,000007,vette,trojans,today,romashka,puppies,possum,pa55word,oakley,moneys,kingpin,golfball,funny,doughboy,dalton,crash,charlotte,carlton,breeze,billie,beast,achilles,tatiana,studio,sterlin,plumber,patrick1,miles,kotenok,homers,gbpltw,gateway1,franky,durango,drake,deeznuts,cowboys1,ccbill,brando,9876543210,zzzz,zxczxc,vkontakte,tyrone,skinny,rookie,qwqwqw,phillies,lespaul,juliet,jeremiah,igor,homer1,dilligaf,caitlin,budman,atlantic,989898,362436,19851985,vfrcbvrf,verona,technics,svetik,stripper,soleil,september,pinkfloy,noodle,metal,maynard,maryland,kentucky,hastings,gang,frederic,engage,eileen,butthole,bone,azsxdc,agent007,474747,19911991,01011985,triton,tractor,somethin,snow,shane,sassy,sabina,russian,porsche9,pistol,justine,hurrican,gopher,deadman,cutter,coolman,command,chase,california,boris,bicycle,bethany,bearbear,babyboy,73501505,123456k,zvezda,vortex,vipers,tuesday,traffic,toto,star69,server,ready,rafael,omega1,nathalie,microlab,killme,jrcfyf,gizmo1,function,freaks,flamingo,enterprise,eleven,doobie,deskjet,cuddles,church,breast,19941994,19781978,1225,01011970,vladik,unknown,truelove,sweden,striker,stoner,sony,SaUn,ranger1,qqqqq,pauline,nebraska,meatball,marilyn,jethro,hammers,gustav,escape,elliot,dogman,chair,brothers,boots,blow,bella,belinda,babies,1414,titties,syracuse,river,polska,pilot,oilers,nofear,military,macdaddy,hawk,diamond1,dddd,danila,central,annette,128500,zxcasd,warhammer,universe,splash,smut,sentinel,rayray,randall,Password1,panda,nevada,mighty,meghan,mayday,manchest,madden,kamikaze,jennie,iloveyo,hustler,hunter1,horny1,handsome,dthjybrf,designer,demon,cheers,cash,cancel,blueblue,bigger,australia,asdfjkl,321654987,1qaz1qaz,1955,1234qwe,01011981,zaphod,ultima,tolkien,Thomas,thekid,tdutybq,summit,select,saint,rockets,rhonda,retard,rebel,ralph,poncho,pokemon1,play,pantyhos,nina,momoney,market,lickit,leader,kong,jenna,jayjay,javier,eatpussy,dracula,dawson,daniil,cartoon,capone,bubbas,789123,19861986,01011986,zxzxzx,wendy,tree,superstar,super1,ssssssss,sonic,sinatra,scottie,sasasa,rush,robert1,rjirfrgbde,reagan,meatloaf,lifetime,jimmy1,jamesbon,houses,hilton,gofish,charmed,bowser,betty,525252,123456789z,1066,woofwoof,Turkey50,santana,rugby,rfnthbyf,miracle,mailman,lansing,kathryn,Jennifer,giant,front242,firefox,check,boxing,bogdan,bizkit,azamat,apollo13,alan,zidane,tracy,tinman,terminal,starbuck,redhot,oregon,memory,lewis,lancelot,illini,grandma,govols,gordon24,giorgi,feet,fatima,crunch,creamy,coke,cabbage,bryant,brandon1,bigmoney,azsxdcfv,3333333,321123,warlord,station,sayang,rotten,rightnow,mojo,models,maradona,lololo,lionking,jarhead,hehehe,gary,fast,exodus,crazybab,conner,charlton,catman,casey1,bonita,arjay,19931993,19901990,1001,100000,sticks,poiuytrewq,peters,passwort,orioles,oranges,marissa,japanese,holyshit,hohoho,gogo,fabian,donna,cutlass,cthulhu,chewie,chacha,bradford,bigtime,aikido,4runner,21212121,150781,wildfire,utopia,sport,sexygirl,rereirf,reebok,raven1,poontang,poodle,movies,microsof,grumpy,eeyore,down,dong,chocolate,chickens,butch,arsenal1,adult,adriana,19831983,zzzzz,volley,tootsie,sparkle,software,sexx,scotch,science,rovers,nnnnnn,mellon,legacy,julius,helen,happyday,fubar,danie,cancun,br0d3r,beverly,beaner,aberdeen,44444,19951995,13243546,123456aa,wilbur,treasure,tomato,theodore,shania,raiders1,natural,kume,kathy,hamburg,gretchen,frisco,ericsson,daddy1,cosmo,condom,comics,coconut,cocks,Check,camilla,bikini,albatros,1Passwor,1958,1919,143143,0.0.0.000,zxcasdqwe,zaqxsw,whisper,vfvekz,tyler1,Sojdlg123aljg,sixers,sexsexsex,rfhbyf,profit,okokok,nancy,mikemike,michaela,memorex,marlene,kristy,jose,jackson1,hope,hailey,fugazi,fright,figaro,excalibu,elvira,dildo,denali,cruise,cooter,cheng,candle,bitch1,attack,armani,anhyeuem,78945612,222333,zenith,walleye,tsunami,trinidad,thomas1,temp,tammy,sultan,steve1,slacker,selena,samiam,revenge,pooppoop,pillow,nobody,kitty1,killer1,jojojo,huskies,greens,greenbay,greatone,fuckin,fortuna,fordf150,first,fashion,fart,emerson,davis,cloud9,china,boob,applepie,alien,963852741,321456,292929,1998,1956,18436572,tasha,stocks,rustam,rfrnec,piccolo,orgasm,milana,marisa,marcos,malaka,lisalisa,kelly1,hithere,harley1,hardrock,flying,fernand,dinosaur,corrado,coleman,clapton,chief,bloody,anfield,636363,420247,332211,voyeur,toby,texas1,surf,steele,running,rastaman,pa55w0rd,oleg,number1,maxell,madeline,keywest,junebug,ingrid,hollywood,hellyeah,hayley,goku,felicia,eeeeee,dicks,dfkthbz,dana,daisy1,columbus,charli,bonsai,billy1,aspire,9999999,987987,50cent,000001,xxxxxxx,wolfie,viagra,vfksirf,vernon,tang,swimmer,subway,stolen,sparta,slutty,skywalker,sean,sausage,rockhard,ricky,positive,nyjets,miriam,melissa1,krista,kipper,kcj9wx5n,jedi,jazzman,hyperion,happy123,gotohell,garage,football1,fingers,february,faggot,easy,dragoon,crazy1,clemson,chanel,canon,bootie,balloon,abc12345,609609609,456321,404040,162534,yosemite,slider,shado,sandro,roadkill,quincy,pedro,mayhem,lion,knopka,kingfish,jerkoff,hopper,everest,ddddddd,damnit,cunts,chevy1,cheetah,chaser,billyboy,bigbird,bbbb,789987,1qa2ws3ed,1954,135246,123789456,122333,1000,050505,wibble,valeria,tunafish,trident,thor,tekken,tara,starship,slave,saratoga,romance,robotech,rich,rasputin,rangers1,powell,poppop,passwords,p0015123,nwo4life,murder,milena,midget,megapass,lucky13,lolipop,koshka,kenworth,jonjon,jenny1,irish1,hedgehog,guiness,gmoney,ghetto,fortune,emily1,duster,ding,davidson,davids,dammit,dale,crysis,bogart,anaconda,alibaba,airbus,7753191,515151,20102010,200000,123123q,12131415,10203,work,wood,vladislav,vfczyz,tundra,Translator,torres,splinter,spears,richards,rachael,pussie,phoenix1,pearl,monty,lolo,lkjhgf,leelee,karolina,johanna,jensen,helloo,harper,hal9000,fletch,feather,fang,dfkthf,depeche,barsik,789789789,757575,727272,zorro,xtreme,woman,vitalik,vermont,train,theboss,sword,shearer,sanders,railroad,qwer123,pupsik,pornos,pippen,pingpong,nikola,nguyen,music1,magicman,killbill,kickass,kenshin,katie1,juggalo,jayhawk,java,grapes,fritz,drew,divine,cyclops,critter,coucou,cecilia,bristol,bigsexy,allsop,9876,1230,01011989,wrestlin,twisted,trout,tommyboy,stefano,song,skydive,sherwood,passpass,pass1234,onlyme,malina,majestic,macross,lillian,heart,guest,gabrie,fuckthis,freeporn,dinamo,deborah,crawford,clipper,city,better,bears,bangbang,asdasdasd,artemis,angie,admiral,2003,020202,yousuck,xbox360,werner,vector,usmc,umbrella,tool,strange,sparks,spank,smelly,small,salvador,sabres,rupert,ramses,presto,pompey,operator,nudist,ne1469,minime,matador,love69,kendall,jordan1,jeanette,hooter,hansen,gunners,gonzo,gggggggg,fktrcfylhf,facial,deepthroat,daniel1,dang,cruiser,cinnamon,cigars,chico,chester1,carl,caramel,calico,broadway,batman1,baddog,778899,2128506,123456r,0420,01011988,z1x2c3,wassup,wally,vh5150,underdog,thesims,thecat,sunnyday,snoopdog,sandy1,pooter,multiplelo,magick,library,kungfu,kirsten,kimber,jean,jasmine1,hotshot,gringo,fowler,emma,duchess,damage,cyclone,Computer,chong,chemical,chainsaw,caveman,catherine,carrera,canadian,buster1,brighton,back,australi,animals,alliance,albion,969696,555777,19721972,19691969,1024,trisha,theresa,supersta,steph,static,snowboar,sex123,scratch,retired,rambler,r2d2c3po,quantum,passme,over,newbie,mybaby,musica,misfit,mechanic,mattie,mathew,mamapapa,looser,jabroni,isaiah,heyhey,hank,hang,golfgolf,ghjcnjnfr,frozen,forfun,fffff,downtown,coolguy,cohiba,christopher,chivas,chicken1,bullseye,boys,bottle,bob123,blueboy,believe,becky,beanie,20002000,yzerman,west,village,vietnam,trader,summer1,stereo,spurs,solnce,smegma,skorpion,saturday,samara,safari,renault,rctybz,peterson,paper,meredith,marc,louis,lkjhgfdsa,ktyjxrf,kill,kids,jjjj,ivanova,hotred,goalie,fishes,eastside,cypress,cyber,credit,brad,blackhaw,beastie,banker,backdoor,again,192837,112211,westwood,venus,steeler,spawn,sneakers,snapple,snake1,sims,sharky,sexxxx,seeker,scania,sapper,route66,Robert,q123456,Passwor1,mnbvcx,mirror,maureen,marino13,jamesbond,jade,horizon,haha,getmoney,flounder,fiesta,europa,direct,dean,compute,chrono,chad,boomboom,bobby1,bing,beerbeer,apple123,andres,8888888,777888,333666,1357,12345z,030303,01011987,01011984,wolf359,whitey,undertaker,topher,tommy1,tabitha,stroke,staples,sinclair,silence,scout,scanner,samsung1,rain,poetry,pisces,phil,peter1,packer,outkast,nike,moneyman,mmmmmmmm,ming,marianne,magpie,love123,kahuna,jokers,jjjjjjjj,groucho,goodman,gargoyle,fuckher,florian,federico,droopy,dorian,donuts,ddddd,cinder,buttman,benny,barry,amsterda,alfa,656565,1x2zkg8w,19881988,19741974,zerocool,walrus,walmart,vfvfgfgf,user,typhoon,test1234,studly,Shadow,sexy69,sadie1,rtyuehe,rosie,qwert1,nipper,maximum,klingon,jess,idontknow,heidi,hahahaha,gggg,fucku2,floppy,flash1,fghtkm,erotica,erik,doodoo,dharma,deniska,deacon,daphne,daewoo,dada,charley,cambiami,bimmer,bike,bigbear,alucard,absolut,a123456789,4121,19731973,070707,03082006,02071986,vfhufhbnf,sinbad,secret1,second,seamus,renee,redfish,rabota,pudding,pppppppp,patty,paint,ocean,number,nature,motherlode,micron,maxx,massimo,losers,lokomotiv,ling,kristine,kostya,korn,goldstar,gegcbr,floyd,fallout,dawn,custom,christina,chrisbln,button,bonkers,bogey,belle,bbbbb,barber,audia4,america1,abraham,585858,414141,336699,20012001,12345678q,0123,whitesox,whatsup,usnavy,tuan,titty,titanium,thursday,thirteen,tazmania,steel,starfire,sparrow,skidoo,senior,reading,qwerqwer,qazwsx12,peyton,panasoni,paintbal,newcastl,marius,italian,hotpussy,holly1,goliath,giuseppe,frodo,fresh,buckshot,bounce,babyblue,attitude,answer,90210,575757,10203040,1012,01011910,ybrjkfq,wasser,tyson,Superman,sunflowe,steam,ssss,sound,solution,snoop,shou,shawn,sasuke,rules,royals,rivers,respect,poppy,phillips,olivier,moose1,mondeo,mmmm,knickers,hoosier,greece,grant,godfather,freeze,europe,erica,doogie,danzig,dalejr,contact,clarinet,champ,briana,bluedog,backup,assholes,allmine,aaliyah,12345679,100100,zigzag,whisky,weaver,truman,tomorrow,tight,theend,start,southpark,sersolution,roberta,rhfcjnrf,qwerty1234,quartz,premier,paintball,montgom240,mommy,mittens,micheal,maggot,loco,laurel,lamont,karma,journey,johannes,intruder,insert,hairy,hacked,groove,gesperrt,francois,focus,felipe,eternal,edwards,doug,dollars,dkflbckfd,dfktynbyf,demons,deejay,cubbies,christie,celeron,cat123,carbon,callaway,bucket,albina,2004,19821982,19811981,1515,12qw34er,123qwerty,123aaa,10101,1007,080808,zeus,warthog,tights,simona,shun,salamander,resident,reefer,racer,quattro,public,poseidon,pianoman,nonono,michell,mellow,luis,jillian,havefun,gunnar,goofy,futbol,fucku,eduardo,diehard,dian,chuckles,carla,carina,avalanch,artur,allstar,abc1234,abby,4545,1q2w3e4r5,125125,123451,ziggy,yumyum,working,what,wang,wagner,volvo,ufkbyf,twinkle,susanne,superman1,sunshin,strip,searay,rockford,radio,qwertyqwerty,proxy,prophet,ou8122,oasis,mylife,monke,monaco,meowmeow,meathead,Master,leanne,kang,joyjoy,joker1,filthy,emmitt,craig,cornell,changed,cbr600,builder,budweise,boobie,bobobo,biggles,bigass,bertie,amanda1,a1s2d3,784512,767676,235689,1953,19411945,14725836,11223,01091989,01011992,zero,vegas,twins,turbo1,triangle,thongs,thanatos,sting,starman,spike1,smokes,shai,sexyman,sex,scuba,runescape,phish,pepper1,padres,nitram,nickel,napster,lord,jewels,jeanne,gretzky,great1,gladiator,crjhgbjy,chuang,chou,blossom,bean,barefoot,alina,787898,567890,5551212,25252525,02071982,zxcvbnm1,zhong,woohoo,welder,viewsonic,venice,usarmy,trial,traveler,together,team,tango,swords,starter,sputnik,spongebob,slinky,rover,ripken,rasta,prissy,pinhead,papa,pants,original,mustard,more,mohammed,mian,medicine,mazafaka,lance,juliette,james007,hawkeyes,goodboy,gong,footbal,feng,derek,deeznutz,dante,combat,cicero,chun,cerberus,beretta,bengals,beaches,3232,135792468,12345qwe,01234567,01011975,zxasqw12,xxx123,xander,will,watcher,thedog,terrapin,stoney,stacy,something,shang,secure,rooney,rodman,redwing,quan,pony,pobeda,pissing,philippe,overkill,monalisa,mishka,lions,lionel,leonid,krystal,kosmos,jessic,jane,illusion,hoosiers,hayabusa,greene,gfhjkm123,games,francesc,enter1,confused,cobra1,clevelan,cedric,carole,busted,bonbon,barrett,banane,badgirl,antoine,7779311,311311,2345,187187,123456s,123456654321,1005,0987,01011993,zippy,zhei,vinnie,tttttttt,stunner,stoned,smoking,smeghead,sacred,redwood,Pussy1,moonlight,momomo,mimi,megatron,massage,looney,johnboy,janet,jagger,jacob1,hurley,hong,hihihi,helmet,heckfy,hambone,gollum,gaston,f**k,death1,Charlie,chao,cfitymrf,casanova,brent,boricua,blackjack,blablabla,bigmike,bermuda,bbbbbbbb,bayern,amazing,aleksey,717171,12301230,zheng,yoyo,wildman,tracker,syncmaster,sascha,rhiannon,reader,queens,qing,purdue,pool,poochie,poker,petra,person,orchid,nuts,nice,lola,lightning,leng,lang,lambert,kashmir,jill,idiot,honey1,fisting,fester,eraser,diao,delphi,dddddddd,cubswin,cong,claudio,clark,chip,buzzard,buzz,butts,brewster,bravo,bookworm,blessing,benfica,because,babybaby,aleksandra,6666666,1997,19961996,19791979,1717,1213,02091987,02021987,xiao,wild,valencia,trapper,tongue,thegreat,sancho,really,rainman,piper,peng,peach,passwd,packers1,newpass6,neng,mouse1,motley,morning,midway,Michelle,miao,maste,marin,kaylee,justin1,hokies,health,glory,five,dutchess,dogfood,comet,clouds,cloud,charles1,buddah,bacardi,astrid,alphabet,adams,19801980,147369,12qwas,02081988,02051986,02041986,02011985,01011977,xuan,vedder,valeri,teng,stumpy,squash,snapon,site,ruan,roadrunn,rjycnfynby,rhtdtlrj,rambo,pizzas,paula,novell,mortgage,misha,menace,maxim,lori,kool,hanna,gsxr750,goldwing,frisky,famous,dodge1,dbrnjh,christmas,cheese1,century,candice,booker,beamer,assword,army,angus,andromeda,adrienne,676767,543210,2010,1369,12345678a,12011987,02101985,02031986,02021988,zhuang,zhou,wrestling,tinkerbell,thumbs,thedude,teddybea,sssss,sonics,sinister,shannon1,satana,sang,salomon,remote,qazzaq,playing,piao,pacers,onetime,nong,nikolay,motherfucker,mortimer,misery,madison1,luan,lovesex,look,Jessica,handyman,hampton,gromit,ghostrider,doghouse,deluxe,clown,chunky,chuai,cgfhnfr,brewer,boxster,balloons,adults,a1a1a1,794613,654123,24682468,2005,1492,1020,1017,02061985,02011987,*****,zhun,ying,yang,windsor,wedding,wareagle,svoboda,supreme,stalin,sponge,simon1,roadking,ripple,realmadrid,qiao,PolniyPizdec0211,pissoff,peacock,norway,nokia6300,ninjas,misty1,medusa,medical,maryann,marika,madina,logan1,lilly,laser,killers,jiang,jaybird,jammin,intel,idontkno,huai,harry1,goaway,gameover,dino,destroy,deng,collin,claymore,chicago1,cheater,chai,bunny1,blackbir,bigbutt,bcfields,athens,antoni,abcd123,686868,369963,1357924680,12qw12,1236987,111333,02091986,02021986,01011983,000111,zhuai,yoda,xiang,wrestle,whiskers,valkyrie,toon,tong,ting,talisman,starcraf,sporting,spaceman,southpar,smiths,skate,shell,seng,saleen,ruby,reng,redline,rancid,pepe,optimus,nova,mohamed,meister,marcia,lipstick,kittykat,jktymrf,jenn,jayden,inuyasha,higgins,guai,gonavy,face,eureka,dutch,darkman,courage,cocaine,circus,cheeks,camper,br549,bagira,babyface,7uGd5HIp2J,5050,1qaz2ws,123321a,02081987,02081984,02061986,02021984,01011982,zhai,xiong,willia,vvvvvv,venera,unique,tian,sveta,strength,stories,squall,secrets,seahawks,sauron,ripley,riley,recovery,qweqweqwe,qiong,puddin,playstation,pinky,phone,penny1,nude,mitch,milkman,mermaid,max123,maria1,lust,loaded,lighter,lexus,leavemealone,just4me,jiong,jing,jamie1,india,hardcock,gobucks,gawker,fytxrf,fuzzy,florida1,flexible,eleanor,dragonball,doudou,cinema,checkers,charlene,ceng,buffy1,brian1,beautifu,baseball1,ashlee,adonis,adam12,434343,02031984,02021985,xxxpass,toledo,thedoors,templar,sullivan,stanford,shei,sander,rolling,qqqqqqq,pussey,pothead,pippin,nimbus,niao,mustafa,monte,mollydog,modena,mmmmm,michae,meng,mango,mamama,lynn,love12,kissing,keegan,jockey,illinois,ib6ub9,hotbox,hippie,hill,ghblehjr,gamecube,ferris,diggler,crow,circle,chuo,chinook,charity,carmel,caravan,cannabis,cameltoe,buddie,bright,bitchass,bert,beowulf,bartman,asia,armagedon,ariana,alexalex,alenka,ABC123,987456321,373737,2580,21031988,123qq123,12345t,1234567890a";
        String [] str=mostCommonPasswords.split(",");
        boolean flag=false;
        for (String s : str) {
            if(input.contains(s)) {
                flag=true;
                sug+="Remove \""+s+"\" since it's very Common!\n";
                break;
            }
        }
        return !flag;
    }

    public static String hive(String input) {
        int n = input.length();
        int digitCount=0,upperCount=0,lowerCount=0,specialCount=0;
        Set<Character> set = new HashSet<>(Arrays.asList('~','`','!', '@', '#', '$', '%', '^', '&', '*', '(', ')','-', '+','_','=','[',']','{','}',':',';','\'','"','<','>',',','.','?','/','.','|','\\',' '));
        for (char i : input.toCharArray()) {
            if (Character.isLowerCase(i))
                lowerCount++;
            if (Character.isUpperCase(i))
                upperCount++;
            if (Character.isDigit(i))
                digitCount++;
            if (set.contains(i))
                specialCount++;
        }
        String ans="";
        if(digitCount>0&&upperCount>0&&lowerCount>0&&specialCount>0) {
            switch (n) {
                case 4:
                case 5:ans="Instantly";
                    break;
                case 6:ans="5 secs";
                    break;
                case 7:ans="6 mins";
                    break;
                case 8:ans="8 hours";
                    break;
                case 9:ans="3 weeks";
                    break;
                case 10:ans="5 years";
                    break;
                case 11:ans="400 years";
                    break;
                case 12:ans="34k years";
                    break;
                case 13:ans="2m years";
                    break;
                case 14:ans="200m years";
                    break;
                case 15:ans="15bn years";
                    break;
                case 16:ans="1tn years";
                    break;
                case 17:ans="93tn years";
                    break;
                case 18:ans="7qd years";
                    break;
                default:ans="forever";
                    break;
            }
        } else if((digitCount>0&&upperCount>0&&lowerCount>0&&specialCount==0)||(digitCount>0&&upperCount>0&&lowerCount==0&&specialCount>0)||(digitCount>0&&upperCount==0&&lowerCount>0&&specialCount>0)||(digitCount==0&&upperCount>0&&lowerCount>0&&specialCount>0)) {
            switch (n) {
                case 4:
                case 5:ans="Instantly";
                    break;
                case 6:ans="1 sec";
                    break;
                case 7:ans="1 min";
                    break;
                case 8:ans="1 hour";
                    break;
                case 9:ans="3 days";
                    break;
                case 10:ans="7 months";
                    break;
                case 11:ans="41 years";
                    break;
                case 12:ans="2k years";
                    break;
                case 13:ans="100k years";
                    break;
                case 14:ans="9m years";
                    break;
                case 15:ans="600m years";
                    break;
                case 16:ans="37bn years";
                    break;
                case 17:ans="2tn years";
                    break;
                case 18:ans="100 tn years";
                    break;
                default:ans="forever";
                    break;
            }
        } else if((digitCount==0&&upperCount>0&&lowerCount>0&&specialCount==0)||(digitCount>0&&upperCount>0&&lowerCount==0&&specialCount==0)||(digitCount==0&&upperCount==0&&lowerCount>0&&specialCount>0)||(digitCount>0&&upperCount==0&&lowerCount==0&&specialCount>0)||(digitCount>0&&upperCount==0&&lowerCount>0&&specialCount==0)||(digitCount==0&&upperCount>0&&lowerCount==0&&specialCount>0)) {
            switch (n) {
                case 4:
                case 5:
                case 6:ans="Instantly";
                    break;
                case 7:ans="25 secs";
                    break;
                case 8:ans="22 mins";
                    break;
                case 9:ans="19 hours";
                    break;
                case 10:ans="1 month";
                    break;
                case 11:ans="5 years";
                    break;
                case 12:ans="300 years";
                    break;
                case 13:ans="16k years";
                    break;
                case 14:ans="800k years";
                    break;
                case 15:ans="43m years";
                    break;
                case 16:ans="2bn years";
                    break;
                case 17:ans="100bn years";
                    break;
                case 18:ans="6tn years";
                    break;
                default:ans="forever";
                    break;
            }
        } else if((digitCount==0&&upperCount==0&&lowerCount>0&&specialCount==0)||(digitCount==0&&upperCount>0&&lowerCount==0&&specialCount==0)) {
            switch (n) {
                case 4:
                case 5:
                case 6:
                case 7:ans="Instantly";
                    break;
                case 8:ans="5 secs";
                    break;
                case 9:ans="2 mins";
                    break;
                case 10:ans="58 mins";
                    break;
                case 11:ans="1 day";
                    break;
                case 12:ans="3 weeks";
                    break;
                case 13:ans="1 year";
                    break;
                case 14:ans="51 years";
                    break;
                case 15:ans="1k years";
                    break;
                case 16:ans="34k years";
                    break;
                case 17:ans="800k years";
                    break;
                case 18:ans="23m years";
                    break;
                case 19:ans="600m years";
                    break;
                case 20:ans="15bn years";
                    break;
                default:ans="forever";
                    break;
            }
        } else if((digitCount>0&&upperCount==0&&lowerCount==0&&specialCount==0)||(digitCount==0&&upperCount==0&&lowerCount==0&&specialCount>0)) {
            switch (n) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:ans="Instantly";
                    break;
                case 11:ans="2 secs";
                    break;
                case 12:ans="25 secs";
                    break;
                case 13:ans="4 mins";
                    break;
                case 14:ans="41 mins";
                    break;
                case 15:ans="6 hours";
                    break;
                case 16:ans="2 days";
                    break;
                case 17:ans="4 weeks";
                    break;
                case 18:ans="9 months";
                    break;
                case 19:ans="7 years";
                    break;
                case 20:ans="79 years";
                    break;
                default:ans="forever";
                    break;
            }
        }
        return ans;
    }

}