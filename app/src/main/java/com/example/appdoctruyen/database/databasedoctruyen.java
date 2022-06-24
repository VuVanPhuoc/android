package com.example.appdoctruyen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.appdoctruyen.model.TaiKhoan;
import com.example.appdoctruyen.model.Truyen;

public class databasedoctruyen extends SQLiteOpenHelper {
    //CSDL

//Tên bảng
    private static String DATABASE_NAME = "doctruyen";
    //Biến tài khoản
    private static String TABLE_TAIKHOAN = "taikhoan";
    private static String ID_TAI_KHOAN = "idtaikhoan";
    private static String TEN_TAI_KHOAN = "tentaikhoan";
    private static String MAT_KHAU = "matkhau";
    private static String PHAN_QUYEN = "phanquyen";
    private static String EMAIL = "email";
    private static int VERSION = 1;
//Biến bảng truyện
    private static String TABLE_TRUYEN = "truyen";
    private static String ID_TRUYEN = "idtruyen";
    private static String TEN_TRUYEN = "tieude";
    private static String NOI_DUNG = "noidung";
    private static String IMAGE = "anh";

//context
    private Context context;
//Biến lưu câu lệnh tạo bảng tài khoản
    private String SQLQuery = "CREATE TABLE "+ TABLE_TAIKHOAN +" ( "+ID_TAI_KHOAN+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TEN_TAI_KHOAN+" TEXT UNIQUE, "
            +MAT_KHAU+" TEXT, "
            +EMAIL+" TEXT, "
            + PHAN_QUYEN+" INTEGER) ";

    //Biến lưu câu lêmhj tạo bảng truyện

    private String SQLQuery1 = "CREATE TABLE "+ TABLE_TRUYEN +" ( "+ID_TRUYEN+" integer primary key AUTOINCREMENT, "
            +TEN_TRUYEN+" TEXT UNIQUE, "
            +NOI_DUNG+" TEXT, "
            +IMAGE+" TEXT, "+ID_TAI_KHOAN+" INTEGER , FOREIGN KEY ( "+ ID_TAI_KHOAN +" ) REFERENCES "+
            TABLE_TAIKHOAN+"("+ID_TAI_KHOAN+"))";

    //Insert dữ liệu vào bảng tài khoản
    //Phân quyền admin và người dùng
    private String SQLQuery2 = "INSERT INTO TaiKhoan VAlUES (null,'admin','admin','admin@gmail.com',2)";
    private String SQLQuery3 = "INSERT INTO TaiKhoan VAlUES (null,'phuoc','phuoc','vuvanphuoc@gmail.com',1)";

    //insert truyện
    private String SQLQuery4 = "INSERT INTO truyen VALUES (null,'Ai làm được','Phần 1:\n" +
            "\n" +
            "Ông Bạch Khiếu Nhàn tuổi đã quá lục tuần mà sức hãy còn mạnh khỏe. Từ khi con gái ông bất hạnh, tủi phận thon von[1] nên ít muốn đi chơi, cứ lui cui ở nhà hoặc sửa kiểng xem hoa, hoặc uống trà đọc sách.\n" +
            "\n" +
            "Năm 1894, một buổi chiều kia gió xuân mát mẻ, nước lớn đầy sông, cỏ cây tươi tốt, Bạch Khiếu Nhàn mình mặc áo quần toàn bằng lụa trắng, vai vắt khăn nhiễu đỏ, thủng thẳng đi dọc theo mé sông Cà Mau mà hứng mát. Khi ông dừng chơn đứng coi sắp nhỏ lội đua, khi ông mỉm cười bầy chó rượt nhau cắn lộn.\n" +
            "\n" +
            "Mặt trời chen lặn, gió càng thêm mát mẻ, Khiếu Nhàn đi lần tới quán cơm Chú Lỳ, tuy chưa mỏi chơn, song ông khát nước.\n" +
            "\n" +
            "Ý nghĩa câu chuyện phần 1: truyện giáo dục đức tính kiên trì, siêng năng, nhẫn nại. Những người nhanh nhẹn nhưng cẩu thả trong suy nghĩ cuối cùng cũng sẽ bị đánh bại bởi người kiên nhẫn, siêng năng dù họ chậm hơn rất nhiều.\n" +
            "\n" +
            "Phần 2:\n" +
            "\n" +
            "Chú Lỳ ngó thấy ông lật đật chào mừng và mời ông vào quán nước. Khiếu Nhàn bước vào kéo ghế mà ngồi, ngó quanh quất không thấy khách ăn uống, duy có một người trai trạc chừng mười bảy, mười tám tuổi, đương ngồi tại bàn gần cửa mà viết. Ông thấy người miệng rộng, môi dầy, vai ngang, trán chợt, tóc hớt cụt, mắt rạng ngời, tư cách nghiêm trang, mặt mày sáng rỡ, tuy y phục tầm thường mà hình dung không phải như người thường, bởi vậy ông cứ ngồi ngó hoài.\n"+
            "Người trai ấy viết ít chữ rồi ngó sững phía ngoài đường, ngó rồi day vô viết, rồi lại ngâm nho nhỏ. Chú Lỳ rót một chén nước trà nóng đem lại mời Khiếu Nhàn uống. Khiếu Nhàn tay bưng chén nước mà mắt ngó người trai ấy hoài.','https://www.sachhayonline.com/data/book-photos/19.jpg',1)";
    private String SQLQuery5 = "INSERT INTO truyen VALUES (null,'Băn khoăn','Mùa đông đã đến rồi trời lạnh buốt, Thỏ con không có gì để ăn cả. Thỏ con mặc áo vào rồi ra ngoài kiếm thức ăn. Nó đi mãi đi mãi cuối cùng cũng tìm được hai củ cải trắng. Thỏ con reo lên:\n" +
            "\n" +
            "– Ôi, ở đây có hai củ cải trắng liền, mình thật là may mắn!\n" +
            "\n" +
            "Thỏ con đói bụng, muốn ăn lắm rồi. Nhưng Thỏ lại nghĩ:\n" +
            "\n" +
            "– Ừm… trời lạnh thế này, chắc Dê con cũng không có cái gì để ăn đâu. Mình phải mang cho Dê con một củ mới được.\n" +
            "\n" +
            "Thế là Thỏ con đi sang nhà bạn Dê nhưng Dê con không có nhà nên Thỏ đặt củ cải lên bàn rồi đi về.\n" +
            "\n" +
            "Tình cờ, Dê con đi chơi cũng tìm được một củ cải trắng nhưng nó chỉ ăn trước một nửa.\n" +
            "\n" +
            "Về đến nhà, lại thấy có một củ cải trắng ở trên bàn Dê thèm ăn lắm, nhưng lại nghĩ:\n" +
            "\n" +
            "– Ôi trời lạnh thế này chắc Hươu con không có cái gì để ăn rồi, mình phải mang cho Hươu con mới được.\n" +
            "\n" +
            "Dê con đến nhà Hươu nhưng Hươu lại đi vắng, Dê con bèn đặt củ cải ở trên bàn rồi về.\n" +
            "\n" +
            "Khi Hươu về nhà, thấy củ cải ở trên bàn, Hươu ngạc nhiên lắm.\n" +
            "\n" +
            "– Ồ, củ cải trắng ở đâu mà ngon vậy nhỉ. Xuỵt… thích quá. Nhưng chắc trời lạnh thế này, Thỏ con cũng không có gì ăn đâu. Mình phải mang sang cho Thỏ mới được.\n" +
            "\n" +
            "Khi Hươu đến thì Thỏ con đang ngủ rất say. Khi tỉnh dậy Thỏ lại thấy trên bàn mình xuất hiện một củ cải trắng.Thỏ vui lắm nó chạy đi gọi các bạn:\n" +
            "\n" +
            "– Bạn Hươu ơi, bạn Dê ơi hãy đến nhà tôi, chúng ta cùng ăn củ cải trắng thơm ngon này.\n" +
            "\n" +
            "Thế là cuối cùng, củ cải trắng ấy được chia sẻ cho cả ba người bạn tốt bụng của chúng ta. Các bạn thấy đấy tấm lòng thơm thảo, sẵn sàng sẻ chia của các bạn ấy thật là đáng học tập phải không nào?\n" +
            "\n" +
            "Ý nghĩa câu chuyện: Khi cho đi bạn sẽ nhận lại được nhiều hơn những thứ mình có.','https://www.sachhayonline.com/data/book-photos/13.jpg',1)";
    private String SQLQuery6 = "INSERT INTO truyen VALUES (null,'Bức thư hối hận','Dê đen và Dê trắng cùng sống trong một khu rừng. Hàng ngày, cả hai thường đến uống nước và tìm cái ăn ở trong khu rừng quen thuộc.\n" +
            "\n" +
            "Một hôm, Dê trắng đi tìm cái ăn và uống nước suối như mọi khi. Dê đang mải mê ngặm cỏ, bất chợt một con Sói ở đâu nhảy xổ ra. Sói quát hỏi:\n" +
            "\n" +
            "- Dê kia! Mi đi đâu?\n" +
            "\n" +
            "Dê trắng sợ rúm cả người, lắp bắp:\n" +
            "\n" +
            "– Dạ, dạ, tôi đi tìm… tìm cỏ non và…và uống nước suối ạ!\n" +
            "\n" +
            "Sói lại quát hỏi:\n" +
            "\n" +
            "– Mi có gì ở chân?\n" +
            "\n" +
            "– Dạ, dạ, chân của tôi có móng ạ…ạ!\n" +
            "\n" +
            "– Trên đầu mi có gì?\n" +
            "\n" +
            "– Dạ, dạ, trên đầu tôi có đôi sừng mới nhú…\n" +
            "\n" +
            "Sói càng quát to hơn:\n" +
            "\n" +
            "– Trái tim mi thế nào?\n" +
            "\n" +
            "– Ôi, ôi, trái…trái tim tôi đang run sợ…sợ…\n" +
            "\n" +
            "– Hahaha…\n" +
            "\n" +
            "Sói cười vang rồi ăn thịt chú Dê trắng tội nghiệp\n" +
            "\n" +
            "Dê đen cũng đi tới khu rừng để ăn cỏ non và uống nước suối. Đang tha thẩn ngặm cỏ, chợt Sói xuất hiện, nó quát hỏi:\n" +
            "\n" +
            "– Dê kia, mi đi đâu?\n" +
            "\n" +
            "Dê đen nhìn con Sói từ đầu tới chân rồi ngước cổ trả lời:\n" +
            "\n" +
            "– Ta đi tìm kẻ nào thích gây sự đây!\n" +
            "\n" +
            "Sói bị bất ngờ, nó hỏi tiếp:\n" +
            "\n" +
            "– Thế dưới chân mi có gì?\n" +
            "\n" +
            "– Chân thép của ta có móng bằng đồng.\n" +
            "\n" +
            "– Thế…thế…trên đầu mi có gì?\n" +
            "\n" +
            "– Trên đầu của ta có đôi sừng bằng kim cương!\n" +
            "\n" +
            "Sói sợ lắm rồi, nhưng vẫn cố hỏi:\n" +
            "\n" +
            "– Mi…mi…trái tim mi thế nào?\n" +
            "\n" +
            "Dê đen dõng dạc trả lời:\n" +
            "\n" +
            "– Trái tim thép của ta bảo ta rằng: hãy cắm đôi sừng kim cương vào đầu Sói. Nào, Sói hãy lại đây.\n" +
            "\n" +
            "Ôi trời, sợ quá, con Sói ba chân bốn cẳng chạy biến vào rừng, từ đó không ai trông thấy nó lởn vởn ở khu rừng đó nữa.\n" +
            "\n" +
            "Ý nghĩa câu chuyện: Qua câu chuyện ngụ ngôn trên, bạn có thể truyền tải nhiều thông điệp khác nhau cho bé hiểu. Chẳng hạn như biết cách ứng xử trước các tình huống khó, nguy hiểm, lạc quan và bản lĩnh để xử lý vấn đề.','https://www.sachhayonline.com/data/book-photos/42.png',1)";
    private String SQLQuery7 = "INSERT INTO truyen VALUES (null,'Chân trời cũ','Một chú bé chăn cừu cho chủ thả cừu gần một khu rừng rậm cách làng không xa lắm. Chăn cừu được ít lâu, chú cảm thấy công việc chăn cừu thực là nhàm chán. Tất cả mọi việc chú có thể làm để giải khuây là nói chuyện với con chó hoặc thổi chiếc sáo chăn cừu của mình.\n" +
            "\n" +
            "Một hôm, trong lúc đang ngắm nhìn đàn cừu và cánh rừng yên tĩnh chú bé chợt nhớ tới lời chủ của chú từng dặn rằng khi sói tấn công cừu thì phải kêu cứu để dân làng nghe thấy và đánh đuổi nó đi.\n" +
            "\n" +
            "Thế là chú nghĩ ra một trò chơi cho đỡ buồn. Mặc dù chẳng thấy con sói nào, chú cứ chạy về làng và la to:\n" +
            "\n" +
            "– Sói ! Sói !\n" +
            "\n" +
            "Đúng như chú nghĩ, dân làng nghe thấy tiếng kêu bỏ cả việc làm và tức tốc chạy ra cánh đồng. Nhưng khi đến nơi, họ chẳng thấy sói đâu, chỉ thấy chú bé ôm bụng cười ngặt nghẽo vì đã lừa được họ.\n" +
            "\n" +
            "Ít ngày sau chú bé chăn cừu lần nữa lại la lên:\n" +
            "\n" +
            "– “Sói ! Sói !”.\n" +
            "\n" +
            "Và một lần nữa dân làng lại chạy ra giúp chú. Nhưng họ lại chẳng thấy con sói nào, chỉ thấy chú bé chăn cừu nghịch ngợm ôm bụng cười khoái chí.\n" +
            "\n" +
            "Thế rồi vào một buổi chiều nọ, khi mặt trời lặn xuống sau cánh rừng và bóng tối bắt đầu phủ đầy lên cánh đồng, một con sói thực sự xuất hiện. Nó nấp sau bụi cây rình bắt những con cừu béo non. Bỗng sói phóng vút ra chộp lấy một chú cừu tội nghiệp. Thấy sói cả đàn cừu sợ hãi chạy toán loạn, chú bé chăn cừu cũng hoảng loạn vô cùng.\n" +
            "\n" +
            "Quá hoảng sợ, chú bé chăn cừu vội chạy về làng và la to:\n" +
            "\n" +
            "– “Sói ! Sói !”.\n" +
            "\n" +
            "Nhưng mặc dù dân làng có nghe tiếng chú kêu, không một ai chạy ra để giúp chú như những lần trước cả. Vì mọi người nghĩ chú lại bày trò nói dối như trước.\n" +
            "\n" +
            "Thế là sói thỏa sức bắt mồi, giết chết rất nhiều cừu của chú bé. Sau khi đã chén no nê, nó biến mất vào rừng rậm. Chú bé buồn bã ngồi giữa đồng cỏ, lòng đầy hối hận về hành động nói dối của mình và hậu quả của trò đùa dại dột gây ra.\n" +
            "\n" +
            "\n" +
            "Ý nghĩa câu chuyện: Nói dối là một tật xấu. Người hay nói dối ngay cả khi nói thật cũng không ai tin.','https://www.sachhayonline.com/data/book-photos/31.jpg',1)";
    private String SQLQuery8 = "INSERT INTO truyen VALUES (null,'Con nhà giàu','Ngày xửa ngày xưa, xưa lắm rồi khi mà muôn thú, cây cỏ, con người còn trò chuyện được với nhau. Trên đồng cỏ rậm ven khu làng có một loài cây gọi là cây đa. Đó là một thứ cây to, khỏe, lá của nó rậm rạp đến nỗi không một tia nắng nào có thể lọt qua được. Vào những ngày trời nắng nóng người ta thường nghỉ chân một lát và trò chuyện hàn huyên cùng cây dưới bóng cây mát rượi. Mọi người ai cũng biết rằng cây đa rất thông thái vì cây đã có tuổi, đã từng trải.\n" +
            "\n" +
            "\n" +
            "Một hôm, có một cậu bé chăn cừu ngồi nghỉ mát dưới gốc cây sau một ngày dài phơi mình dưới nắng cậu bé thấy người mệt mỏi và nóng bức. Một làn gió mơn man thổi thoa nhẹ lên tấm thân mỏi mệt của chú bé. Cậu bé bắt đầu thấy buồn ngủ. Vừa đặt mình xuống cậu bé bỗng ngước mắt nhìn lên những cành cây. Bấy giờ cậu bé bỗng thấy mình thật kiêu hãnh, cậu vẫn thường hay khoe với mọi người rằng cậu có tài chăn cừu và đàn cừu của cậu nhờ vậy mà lớn rất nhanh. Khi cậu bé phát hiện ra cây đa chỉ có những chùm quả rất nhỏ, nó bắt đầu thấy ngạc nhiên. Cậu bắt đầu chế giễu: hư, một cái cây to khỏe thế này mà làm sao chỉ có những bông hoa những chùm quả bé tí tẹo thế kia, mọi người vẫn bảo là cái cây này thông thái lắm kia mà nhưng làm sao nó có thể thông thái khi mà quả của nó chỉ toàn bé xíu như vậy. Dĩ nhiên là cây đa nghe hết những lời của cậu bé nhưng cây vẫn im lặng và cành lá chỉ khẽ rung rinh đủ để cho gió cất lên khúc hát ru êm dịu. Cậu bé bắt đầu ngủ, cậu ngáy o o…. Cốc.\n" +
            "Quả đa nhỏ rụng chính giữa trán của cậu bé, nó bừng tỉnh nhưng càu nhàu: “Gừm… người ta vừa mới chợp mắt được có một tí”, rồi nó nhặt quả đa lên chưa hết chưa biết định làm gì với quả đa này bỗng nhiên cậu bé nghe thấy có tiếng cười khúc khích, cậu nghe thấy cây hỏi:\n" +
            "– Có đau không ?.\n" +
            "– Không nhưng mà làm người ta mất cả giấc ngủ .\n" +
            "– Đó là bài học cho cậu bé to đầu đấy. Cậu chẳng vừa nhạo tôi là chỉ sinh ra toàn những quả nhỏ xíu là gì.\n" +
            "– Tôi nhạo đấy tại sao người đời lại bảo bác là thông thái được nhỉ? Phá giấc ngủ trưa của người khác! Thế cũng là thông minh chắc!.\n" +
            "Cây cười và nói: này này anh bạn anh hãy nghe đây những chiếc lá của tôi cho cậu bóng mát để cậu lấy chỗ nghỉ ngơi. Ừ thì cứ cho là quả của tôi nó bé đi chăng nữa nhưng chẳng lẽ cậu không thấy rằng tạo hóa hoạt động rất hoàn chỉnh đó sao. Cậu thử tưởng tượng xem, nếu quả của tôi to như quả dừa thì điều gì sẽ xảy ra khi nó rơi vào đầu cậu.\n" +
            "Cậu bé im thin thít: ừ nhở. Cậu chưa hề nghĩ đến điều này bao giờ cả.\n" +
            "Cây lại nhẹ nhàng tiếp lời:\n" +
            "– Những người khiêm tốn có thể học hỏi rất nhiều điều từ việc quan sát những vật xung quanh đấy cậu bé ạ.\n" +
            "– Vâng bác đa bác cứ nói tiếp đi.\n" +
            "– Cậu hãy bắt đầu làm bạn với những gì ở quanh cậu. Chúng ta tất cả đều cần tới nhau. Cậu cứ nhìn bầy ong kia mà xem. Nhờ có ong mà hoa của tôi mới có thể trở thành quả. Thế còn bầy chim kia thì sao. Chúng làm tổ ngay giữa tán lá của tôi đây này. Những con chim bố mẹ kia phải làm việc vất vả cả ngày để bắt sâu nuôi con và cậu có biết việc làm đó có ý nghĩa gì với tôi không?.\n" +
            "– Không, có ý nghĩa gì vậy hả bác?.\n" +
            "– Sâu ăn lá chính vậy loài chim kia chính là những người bạn của tôi. Chúng còn giúp cả cậu nữa đấy, sở dĩ cừu của cậu có đủ lá và cỏ để ăn là vì chim chóc đã tiêu diệt hết các loài côn trùng và sâu bọ. Và chưa hết đâu cậu bé ạ!.\n" +
            "– Còn gì thế nữa hả bác đa.\n" +
            "– Cậu hãy nhìn xuống chân mình mà xem, những chiếc lá rụng tạo thành lớp thảm mục, những con sâu đào đất ngoi lên để ăn lá, chúng đào đất thành những lỗ nhỏ, nhờ đó không khí có thể vào được trong đất. Có không khí trong đất nên bộ rễ của tôi mới khỏe thế nào đấy. Rễ khỏe nên tôi cũng khỏe hơn. Nào thế bây giờ cậu trẻ đã hiểu chưa?.\n" +
            "– Cháu hiểu rồi thưa bác. Bác tha lỗi cho cháu nhé vì đã cười nhạo bác bác đa ạ.\n" +
            "– Không sao bây giờ cháu hãy ra dắt cừu về đi.\n" +
            "\n" +
            "\n" +
            "Ý nghĩa câu chuyện: Có thể cậu bé chăn cừu không phải ngay sau đó sẽ trở nên khiêm tốn, học hỏi luôn được nhưng rõ ràng là cậu đã nhận ra người ta không thể sống lẻ loi được.','https://www.sachhayonline.com/data/book-photos/48.jpg',1)";


    //Tạo bảng tại phương thức này
    public databasedoctruyen(@Nullable Context context) {
        super(context, DATABASE_NAME, null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Thực hiện các câu lệnh truy vấn không trả về kết quả
        db.execSQL(SQLQuery);
        db.execSQL(SQLQuery1);
        db.execSQL(SQLQuery2);
        db.execSQL(SQLQuery3);
        db.execSQL(SQLQuery4);
        db.execSQL(SQLQuery5);
        db.execSQL(SQLQuery6);
        db.execSQL(SQLQuery7);
        db.execSQL(SQLQuery8);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
//Phương thức lấy tất cả tài khoản
    public Cursor getData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res =db.rawQuery("SELECT * FROM "+TABLE_TAIKHOAN,null);
        return res;

    }

    //Phương thức thêm tài khoản
    public void AddTaiKhoan(TaiKhoan taiKhoan){
        SQLiteDatabase db =this.getWritableDatabase();

        //Thực hiện insert thông qua ContentValúes
        ContentValues values =new ContentValues();
        values.put(TEN_TAI_KHOAN,taiKhoan.getmTenTaiKhoan());
        values.put(MAT_KHAU,taiKhoan.getmMatKhau());
        values.put(EMAIL,taiKhoan.getmEmail());
        values.put(PHAN_QUYEN,taiKhoan.getmPhanQuyen());

        db.insert(TABLE_TAIKHOAN,null,values);
        //Đóng lại khi ko dùng đến
        db.close();
        Log.e("ADD TK","TC");
    }
    //Lấy 3 truyện mới nhất
    public Cursor getData1(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res =db.rawQuery("SELECT * FROM "+TABLE_TRUYEN+" ORDER BY "+ID_TRUYEN+" DESC LIMIT 3 ",null);
    return res;
    }

    //Lấy tất cả truyện
    public Cursor getData2(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT*FROM "+TABLE_TRUYEN,null );
        return res;
    }


    //Thêm truyện
    public void AddTruyen(Truyen truyen){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(TEN_TRUYEN,truyen.getTenTruyen());
        values.put(NOI_DUNG,truyen.getNoiDung());
        values.put(IMAGE,truyen.getAnh());
        values.put(ID_TAI_KHOAN,truyen.getID_TK());

        db.insert(TABLE_TRUYEN,null,values);
        db.close();

    }

    //Xoá truyện
    public  int Delete(int i){
        SQLiteDatabase db =this.getReadableDatabase();

        int res =db.delete(TABLE_TRUYEN,ID_TRUYEN+" = "+i,null);
        return res;

    }
}
