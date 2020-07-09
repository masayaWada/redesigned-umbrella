import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

 /**
 * ビデオ名を修正する
 *
 * @masayaWada
 */
public class MovieControl {

    /**
     * @param filePathList ファイル名を格納する配列
     */
    static List<File> filePathList;

    public static void main(final String[] args) {
        // ファイル名を格納する配列
        filePathList = new ArrayList<File>();

        // Fileクラスのオブジェクトを生成する
        final File dir = new File("/Volumes/素材データバンク1/撮影データ/");

        //再帰的にファイル名を取得
        getFile(dir);

        //ファイル名の配列を元にファイル名を変更する
        for(final File filePath : filePathList) {
            // 拡張子を取得
            final String extension = filePath.toString().substring(filePath.toString().lastIndexOf("."));

            // 動画ファイルのみファイル名を変更
            if ( (".mp4".equals(extension)) || (".MP4".equals(extension)) ) {
                // ファイル作成日付を取得
                final String createFileTime = getFileTime(filePath);
                // ファイル作成日を元にファイル名を作成
                final File newFilePath = new File(filePath.getParent().toString() + "/" + createFileTime + ".mp4");
                // ファイル名変更実行
                filePath.renameTo(newFilePath);
            }
        }
    }

    /**
     * 再起的にファイル名を取得する.
     * 
     * @param folderPath フォルダパス
     */
    private static void getFile(final File dir) {

        // listFilesメソッドを使用して一覧を取得する
        final File[] list = dir.listFiles();

        if (list != null) {

            for (int i = 0; i < list.length; i++) {

                // ファイルの場合
                if (list[i].isFile()) {
                    filePathList.add(list[i]);
                }
                // ディレクトリの場合
                else if (list[i].isDirectory()) {
                    // Fileクラスのオブジェクトを生成する
                    final File childDir = new File(list[i].toString() + "/");
                    getFile(childDir);
                }
            }
        } else {
            System.out.println("null");
        }
    }

    /**
     * 対象ファイルのファイル作成日を取得する
     * 
     * @param file 対象ファイル
     * @return ファイル作成日 書式(yyyy-MM-dd HH:mm:ss)
     */
    private static String getFileTime(final File file) {
        BasicFileAttributes attrs;
        String formatted = "";
        try {
            attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            final FileTime time = attrs.creationTime();

            //書式
            final String pattern = "yyyy年MM月dd日 HH-mm-ss";
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            formatted = simpleDateFormat.format(new Date(time.toMillis()));
        } catch (final IOException e) {
        e.printStackTrace();
        }
        //ファイル作成日を返す。
        return formatted;
    }
}