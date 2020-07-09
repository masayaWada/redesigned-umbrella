import java.io.File;
 
 /**
 * ビデオ名を修正する
 *
 * @masayaWada
 */
public class MovieControl {
 
    public static void main(final String[] args) {
        //Fileクラスのオブジェクトを生成する
        final File dir = new File("/Users/Shared/java/");
        
        //listFilesメソッドを使用して一覧を取得する
        final File[] list = dir.listFiles();
        
        if(list != null) {
            System.out.println(list.length);
        } else {
            System.out.println("null");
        }
    }
}