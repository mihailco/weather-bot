package com.orgyflame.springtelegrambotapi.api.method.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.PartialBotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.Message;
import com.orgyflame.springtelegrambotapi.api.object.media.InputMedia;
import com.orgyflame.springtelegrambotapi.api.object.media.InputMediaAnimation;
import com.orgyflame.springtelegrambotapi.api.object.media.InputMediaAudio;
import com.orgyflame.springtelegrambotapi.api.object.media.InputMediaDocument;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 3.5
 * <p>
 * Use this method to send a group of photos, videos, documents or audios as an album.
 * Documents and audio files can be only group in an album with messages of the same type.
 * On success, an array of Messages that were sent is returned.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMediaGroup extends PartialBotApiMethod<ArrayList<Message>> {
    public static final String PATH = "sendMediaGroup";

    public static final String CHATID_FIELD = "chat_id";
    public static final String MEDIA_FIELD = "media";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///<  	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(MEDIA_FIELD)
    @NonNull
    private List<InputMedia> medias; ///< A JSON-serialized array describing photos and videos to be sent, must include 2–10 items
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the messages are a reply, ID of the original message
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the messages silently. Users will receive a notification with no sound.
    @JsonProperty(ALLOWSENDINGWITHOUTREPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public ArrayList<Message> deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<ArrayList<Message>> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<ArrayList<Message>>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending media group", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (medias == null || medias.isEmpty()) {
            throw new TelegramApiValidationException("Media parameter can't be empty", this);
        } else if (medias.size() < 2 || medias.size() > 10) {
            throw new TelegramApiValidationException("Number of media should be between 2 and 10", this);
        }

        for (InputMedia inputMedia : medias) {
            if (inputMedia == null) {
                throw new TelegramApiValidationException("Media parameter can not be empty", this);
            } else if (inputMedia instanceof InputMediaAnimation) {
                throw new TelegramApiValidationException("Media parameter can not be an Animation", this);
            } else {
                inputMedia.validate();
            }
        }

        if (medias.stream().anyMatch(x -> x instanceof InputMediaAudio)) {
            if (!medias.stream().allMatch(x -> x instanceof InputMediaAudio)) {
                throw new TelegramApiValidationException("Media parameter containing Audio can not have other types", this);
            }
        } else if (medias.stream().anyMatch(x -> x instanceof InputMediaDocument)) {
            if (!medias.stream().allMatch(x -> x instanceof InputMediaDocument)) {
                throw new TelegramApiValidationException("Media parameter containing Document can not have other types", this);
            }
        }
    }
}
