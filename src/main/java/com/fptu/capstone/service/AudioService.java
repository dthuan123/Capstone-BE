package com.fptu.capstone.service;

import com.fptu.capstone.entity.Chapter;
import com.fptu.capstone.repository.ChapterRepository;
import com.google.common.html.HtmlEscapers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@Service
public class AudioService {

    @Autowired
    ChapterRepository chapterRepository;

    public byte[] getAudio(int chapterId, boolean female) {
        // Instantiates a client
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            Chapter chapter = chapterRepository.findById(chapterId);
            String text = chapter.getContent();
            text = text.replaceAll("\\<.*?>","");
            //String text = ""Người trẻ tuổi, ta nhìn ngươi xương cốt tinh kỳ, là vạn người không được một võ học kỳ tài, có thể suy nghĩ một chút gia nhập ta Thiết Cốt Tranh Tranh phái làm một tên đệ tử, tương lai đại thành tất nhiên bất khả hạn lượng." Cảnh sắc nghi nhân trong sơn dã, Quân Thường Tiếu cười tủm tỉm đối trước mắt võ giả nói, khóe miệng nâng lên đường cong, phối hợp trắng nõn khuôn mặt cho người ta phi thường anh tuấn cảm giác. Không phải cảm giác, là thật. Nếu có nữ nhân nhìn thấy hắn, khẳng định nghẹn ngào gào lên, khẳng định bị mê ngã trái ngã phải. "Cái kia..." Trên đầu kéo căng lấy Lục Cân võ giả, khách khí nói: "Các hạ có thể hay không trước tiên đem chân dịch chuyển khỏi?" Tầm mắt dời xuống, nguyên lai Quân Thường Tiếu một chân giẫm tại người ta trên mặt, kề sát thảm cỏ thượng khác nửa gương mặt, kỳ thật còn có nhất cái chân to ấn. Đây là bị đánh. Từ ngã xuống đất tư thế cùng ngực lưu lại dấu chân đến xem, bị đánh hoàn không nhẹ. Quân Thường Tiếu thu hồi chân, có chút áy náy nói ra: "Nhìn ngươi đùa bỡn ta phái hoa nhường nguyệt thẹn nữ đệ tử, bản tọa nhất thời nhịn không được, thực sự rất xin lỗi." "..." Lục Cân võ giả im lặng. Dã ngoại gặp phải nhất cái mỹ nữ, chỉ là tiến lên nói hai câu, liền bị tự xưng Thiết Cốt Tranh Tranh phái Chưởng môn đánh một trận, cái này thực sự tặc không may. "Bằng hữu." Quân Thường Tiếu nói ra: "Suy nghĩ một chút a." Lục Cân võ giả yếu ớt mà nói: "Ta nói không đồng ý, có thể chứ?" "Có thể." Quân Thường Tiếu chân thành nói: "Ta Thiết Cốt Tranh Tranh phái luôn luôn lấy đức phục người, tuyệt không ép buộc!" "Thật có lỗi." Áo xanh võ giả chịu đựng đau, xin miễn nói: "Ta đã gia nhập Linh Tuyền tông, không thể trở thành quý phái đệ tử." Mình thật vất vả thông qua Linh Tuyền tông khảo hạch, trở thành ký danh đệ tử, há lại sẽ chuyển ném môn phái khác đâu! Huống chi, Thiết Cốt Tranh Tranh phái? Nghe xong danh tự này, khẳng định là Tinh Vẫn đại lục hoàn toàn bất nhập lưu rác rưởi môn phái, đi cũng là lãng phí thời gian, lãng phí sinh mệnh. "Ai." Quân Thường Tiếu lắc đầu, có chút tiếc hận nói: "Bằng hữu cùng ta Thiết Cốt Tranh Tranh phái vô duyên a." "Thiên Thiên." Hắn quay đầu nói: "Gia hỏa này vừa rồi không có khi dễ ngươi đi?" Cách đó không xa trên đá lớn, ngồi yên lặng một tên nữ tử áo trắng, hai con ngươi như suối nước thanh tịnh, da thịt như tuyết, mặc dù chỉ có mười sáu mười bảy tuổi, lại có mỹ nữ tất cả cứng rắn điều kiện. Nếu như muốn nói khuyết điểm, bởi vì tuổi tác duyên cớ, nên vểnh lên địa phương còn không có nhếch lên tới. "Chưởng môn." Lục Thiên Thiên chớp chớp ống tay áo, thản nhiên nói: "Hắn vừa rồi muốn sờ tay của ta." "Ba!" Quân Thường Tiếu lần nữa nhấc chân giẫm tại áo xanh võ giả trên mặt, mục quang lãnh lệ nói: "Ngươi đây là chán sống." Lúc trước hoàn một mặt ôn nhu, đột nhiên trở nên đằng đằng sát khí, trở mặt tốc độ tuyệt đối so lật sách nhanh. Lục Cân võ giả vội vàng nói: "Các hạ... Ta... Ta không có!" Hắn thề mình mới vừa rồi là có một chút như vậy ý nghĩ, nhưng căn bản không có động thủ liền bị đánh một trận, nhiều lắm là tính chiếm tiện nghi chưa thoả mãn a! Quân Thường Tiếu âm thanh lạnh lùng nói: "Thiên Thiên sẽ không nói dối, bản tọa tin tưởng nàng, hôm nay nhất định phải hảo hảo giáo huấn ngươi một trận!" "Bành!" "Bành!" "Bành!" Trận trận gió mát phất phơ thổi, thổi tới sưng mặt sưng mũi áo xanh võ giả trên mặt, hắn không cảm giác được dễ chịu, bởi vì bị đánh xương cốt đều nhanh tan thành từng mảnh. "Ghê tởm!" Cố gắng ngẩng đầu, nhìn xem dần dần mơ hồ hai cái bóng lưng, Lục Cân võ giả nắm chặt bùn đất hận nhưng nói: "Lão tử nhất định phải báo thù!" ... Quân Thường Tiếu. Thiết Cốt Tranh Tranh phái Chưởng môn. Cái này Thiết Cốt Tranh Tranh phái có thể khó lường. Từ năm đó một tên họ Vương võ giả thành lập, nhân trước kia thiết cốt tranh tranh từ vách núi nhảy xuống không có ngã chết, bị người qua đường thưởng một bữa cơm chi ân, sau khai tông lập phái, sáng lập Thiết Cốt Tranh Tranh phái. Môn phái tôn chỉ là —— làm thiết cốt tranh tranh chi nhân, đi thiết cốt tranh tranh sự tình! Đời thứ nhất Chưởng môn chăm lo quản lý, ngắn ngủi hơn mười năm phát triển, liền trong giang hồ xông ra thành tựu, môn phái cũng nhất cử từ bất nhập lưu bước vào Cửu lưu môn phái, trở thành Thanh Dương thôn chói mắt nhất tồn tại! Nhưng mà. Làm sao tính được số trời. Đời thứ nhất Chưởng môn đi trên trấn phong nguyệt nơi chốn vui đùa, bị quan phủ chỗ bắt, lại nhân vượt ngục không thành, tùy ý hỏi trảm. Cả môn phái lập tức rắn mất đầu, từ đó bắt đầu xuống dốc không phanh.";
            String ssml = textToSsml(text);
            SynthesisInput input = SynthesisInput.newBuilder().setSsml(ssml).build();

            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode("vi-VN")
                            .setSsmlGender(SsmlVoiceGender.FEMALE)
                            .build();
            if(!female) {
                voice =
                        VoiceSelectionParams.newBuilder()
                                .setLanguageCode("vi-VN")
                                .setSsmlGender(SsmlVoiceGender.MALE)
                                .build();
            }
            // Select the type of audio file you want returned
            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

            // Perform the text-to-speech request on the text input with the selected voice parameters and
            // audio file type
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            // Write the response to the output file.
            try (OutputStream out = new FileOutputStream("output.mp3")) {
                //out.write(audioContents.toByteArray());
                //System.out.println("Audio content written to file \"output.mp3\"");
                return audioContents.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String textToSsml(String text) {
        String escapedLines = HtmlEscapers.htmlEscaper().escape(text);

        String expandedNewline = escapedLines.replaceAll(",", "<break time='0.5s'/>");
        String ssml = "<speak>" + expandedNewline + "</speak>";

        return ssml;
    }
}






