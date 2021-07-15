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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AudioService {

    @Autowired
    ChapterRepository chapterRepository;

    public byte[] getAudio(int chapterId, boolean female) {
        // Instantiates a client
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
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

            // Set the text input to be synthesized
            Chapter chapter = chapterRepository.findById(chapterId);
            String text = chapter.getContent();
            text = text.replaceAll("\\<.*?>","");
            int part = text.length() / 2500;
            if(text.length() % 2500 != 0) {
                part++;
            }
            List<byte[]> list = new ArrayList();
            for(int i = 0, count = 1; count <= part; i += 2500, count++ ) {
                String textPart = "";
                if(count == part) {
                    textPart = text.substring(i, text.length());
                } else {
                    textPart = text.substring(i, i + 2500);
                }

                String ssml = textToSsml(textPart);
                SynthesisInput input = SynthesisInput.newBuilder().setSsml(ssml).build();
                AudioConfig audioConfig =
                        AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

                SynthesizeSpeechResponse response =
                        textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
                ByteString audioContents = response.getAudioContent();
                list.add(audioContents.toByteArray());
                System.out.println("processing" + i);
            }

            int byteLength = 0;

            for(byte[] arr: list) {
                byteLength += arr.length;
            }
            byte[] allByteArray = new byte[byteLength];
            ByteBuffer target = ByteBuffer.wrap(allByteArray);
            for(byte[] arr: list) {
                target.put(arr);
            }

            try {
                return target.array();
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






