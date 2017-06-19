package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import domain.CommentData;

/**
 * Created by Administrator on 2017/3/19.
 */
public class CommentInfoParse {
    public static CommentData commentInfo(String str){
        CommentData commentData = new CommentData();
        try{
            Type type = new TypeToken<String[]>(){}.getType();
            String []strs = new Gson().fromJson(str,type);
            return new CommentData(strs[0],strs[1],strs[2],strs[3],strs[4],strs[5],strs[6]);
        }catch (Exception e){
        }
        return commentData;
    }
}
