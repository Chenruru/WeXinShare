package com.jo.jingou;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.w3c.dom.Text;

import java.security.KeyRep;

public class MainActivity extends AppCompatActivity {


    //获取注册的App_Id
    private static final String APP_ID="wxbd3e6bba8efbae73";

    // IWXAPI是第三方微信和app通信的接口
    private IWXAPI api;

    private Button toshare,tosharefriends;

    private static int mTargeetScene =SendMessageToWX.Req.WXSceneSession;  //发送给好友
    private static int mTargeetSceneCircle=SendMessageToWX.Req.WXSceneTimeline; //发送到朋友圈
    private Button toshareweb;
    private Button tosharemusic;
    private Button tosharevdeio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regToWx(); //微信初始化。已经微信注册了，可以调用了

         toshare = (Button) findViewById(R.id.toshare);  //分享给好友
         tosharefriends = (Button) findViewById(R.id.tosharefriends);  //分享到朋友圈
        toshareweb = (Button) findViewById(R.id.toshareweb);//分享网址
        tosharemusic = (Button) findViewById(R.id.tosharemusic); //分享音乐
        tosharevdeio = (Button) findViewById(R.id.tosharevdeio);   //分享视频

        //分享给好友
        toshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享文字
                shareText();


            }


        });

        //分享到朋友圈
        tosharefriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareTextToCrile();  //分享文字到朋友圈
                //shareWebToCrile();  //分享网址到朋友圈
            }
        });


        toshareweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享网址
                shareWeb();
            }
        });

        //分享音乐
        tosharemusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toshareMusic(); //分享音乐
            }
        });

        //分享视频
        tosharevdeio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toshareVideo();   //分享视频

            }
        });

    }
    //分享视频
    private void toshareVideo() {
        WXVideoObject musicObject = new WXVideoObject();
        musicObject.videoUrl = "http://music.163.com/m/mv?id=5383969&userid=258307201&from=groupmessage";


        //用WXWebpageObject对象初始化一个WXMediaMessage 对象 填写标题 描述
        WXMediaMessage msg = new WXMediaMessage();

        msg.mediaObject = musicObject;
        msg.title ="三千里";
        msg.description = "C-BLOCK/ 西奥Sio / BBC";

        Bitmap thumb = BitmapFactory.decodeResource(this.getResources() , R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb , true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = mTargeetScene;
        api.sendReq(req);

    }

    //分享音乐给好友
    private void toshareMusic() {

        WXMusicObject musicObject = new WXMusicObject();
        musicObject.musicUrl = "http://music.163.com/#/song?id=494300869&userid=429917799";


        //用WXWebpageObject对象初始化一个WXMediaMessage 对象 填写标题 描述
        WXMediaMessage msg = new WXMediaMessage();

        msg.mediaObject = musicObject;
        msg.title ="消愁";
        msg.description = "毛不易";

        Bitmap thumb = BitmapFactory.decodeResource(this.getResources() , R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb , true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = mTargeetScene;  //分享给好友
        api.sendReq(req);
    }


//    //分享网址到朋友圈
//    private void shareWebToCrile() {
//
//        //初始化一个WXWebpageObject对象，填写分享的网址
//        WXWebpageObject pageobject=new WXWebpageObject();
//        pageobject.webpageUrl="https://www.baidu.com/";  //这就是分享的网址
//
//        WXMediaMessage pagemessage=new WXMediaMessage(pageobject);
//        pagemessage.title="百度";
//        pagemessage.description="搜一搜";  //描述
//
//        Bitmap thumb= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        pagemessage.thumbData=Util.bmpToByteArray(thumb,true);
//        //构造一个Req
//        SendMessageToWX.Req pagereq=new SendMessageToWX.Req();
//        //transaction字段用于标识一个请求
//        pagereq.transaction=buildTransaction("webpage");
//        pagereq.message=pagemessage;
//
//        pagereq.scene=mTargeetSceneCircle;  //分享到朋友圈
//
//        //调用api接口发送数据到微信
//        api.sendReq(pagereq);
//    }

    //分享网址到好友
    private void shareWeb() {
        //初始化一个WXWebpageObject对象，填写分享的网址
        WXWebpageObject pageobject=new WXWebpageObject();
        pageobject.webpageUrl="https://www.baidu.com/";  //这就是分享的网址

        WXMediaMessage pagemessage=new WXMediaMessage(pageobject);
        pagemessage.title="百度";
        pagemessage.description="搜一搜";  //描述

        Bitmap thumb= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        pagemessage.thumbData=Util.bmpToByteArray(thumb,true);
        //构造一个Req
        SendMessageToWX.Req pagereq=new SendMessageToWX.Req();
        //transaction字段用于标识一个请求
        pagereq.transaction=buildTransaction("webpage");
        pagereq.message=pagemessage;

        pagereq.scene=mTargeetScene;  //分享给好友

        //调用api接口发送数据到微信
        api.sendReq(pagereq);

    }

    //分享文本到朋友圈
    private void shareTextToCrile() {
        //初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObject=new WXTextObject();
        textObject.text= "Hello world!";  //这就是分享的文本

        WXMediaMessage wxmessage=new WXMediaMessage();
        wxmessage.mediaObject=textObject; //吧WXTextObject发送出去
        wxmessage.description="Hello world!";   //描述

        //构造一个Req
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=buildTransaction("text");  //buildTransaction这个单词是一个坑，需要写下边一段代码

        //transaction字段用于标识一个请求
        req.message=wxmessage;
        req.scene=mTargeetSceneCircle;  //分享到朋友圈

        //调用api接口发送数据到微信
        api.sendReq(req);

    }


    //分享文字
    private void shareText() {

        //初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObject=new WXTextObject();
        textObject.text= "Hello world!";  //这就是分享的文本

        WXMediaMessage wxmessage=new WXMediaMessage();
        wxmessage.mediaObject=textObject; //吧WXTextObject发送出去
        wxmessage.description="Hello world!";   //描述

        //构造一个Req
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=buildTransaction("text");  //buildTransaction这个单词是一个坑，需要写下边一段代码

        //transaction字段用于标识一个请求
        req.message=wxmessage;
        req.scene=mTargeetScene;  //分享给好友

        //调用api接口发送数据到微信
        api.sendReq(req);

    }

    //必须写这一段代码
    private String buildTransaction(final String type){
        return (type==null)? String.valueOf(System.currentTimeMillis()):type +System.currentTimeMillis();
    }

    private void regToWx() {

        //通过WXAPIFactory  获取IWXAPI实例
        api= WXAPIFactory.createWXAPI(this,APP_ID,false);
        //将应用的app_id注册到微信中  IWXAPI是第三方微信和app通信的接口
        api.registerApp(APP_ID);
    }
}
