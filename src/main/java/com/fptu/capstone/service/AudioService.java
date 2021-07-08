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
            //String text = "Khi một lá thư được gởi đến cho cậu bé Harry Potter bình thường và bất hạnh, cậu khám phá ra một bí mật đã được che giấu suốt cả một thập kỉ. Cha mẹ cậu chính là phù thủy và cả hai đã bị lời nguyền của Chúa tể Hắc ám giết hại khi Harry mới chỉ là một đứa trẻ, và bằng cách nào đó, cậu đã giữ được mạng sống của mình. Thoát khỏi những người giám hộ Muggle không thể chịu đựng nổi để nhập học vào trường Hogwarts, một trường đào tạo phù thủy với những bóng ma và phép thuật, Harry tình cờ dấn thân vào một cuộc phiêu lưu đầy gai góc khi cậu phát hiện ra một con chó ba đầu đang canh giữ một căn phòng trên tầng ba. Rồi Harry nghe nói đến một viên đá bị mất tích sở hữu những sức mạnh lạ kì, rất quí giá, vô cùng nguy hiểm, mà cũng có thể là mang cả hai đặc điểm trên.";
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






