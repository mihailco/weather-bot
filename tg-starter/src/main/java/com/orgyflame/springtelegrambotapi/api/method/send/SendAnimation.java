package com.orgyflame.springtelegrambotapi.api.method.send;

import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.PartialBotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.InputFile;
import com.orgyflame.springtelegrambotapi.api.object.Message;
import com.orgyflame.springtelegrambotapi.api.object.MessageEntity;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.ReplyKeyboard;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound).
 * On success, the sent Message is returned.
 * <p>
 * Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendAnimation extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendAnimation";

    public static final String CHATID_FIELD = "chat_id";
    public static final String ANIMATION_FIELD = "animation";
    public static final String DURATION_FIELD = "duration";
    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String CAPTION_FIELD = "caption";
    public static final String PARSEMODE_FIELD = "parse_mode";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String THUMB_FIELD = "thumb";
    public static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    public static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";

    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Animation to send. Pass a file_id as String to send an animation that exists on the
     * Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation
     * from the Internet, or upload a new animation using multipart/form-data.
     */
    @NonNull
    private InputFile animation;
    private Integer duration; ///< Optional. Duration of sent animation in seconds
    private String caption; ///< Optional. Animation caption (may also be used when resending videos by file_id).
    private Integer width; ///< Optional. Animation width
    private Integer height; ///< Optional. Animation height
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String parseMode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    /**
     * Optional.
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;
    @Singular
    private List<MessageEntity> captionEntities; ///< Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending animation", result);
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

        if (animation == null) {
            throw new TelegramApiValidationException("Animation parameter can't be empty", this);
        }

        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty())) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }

        animation.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (thumb != null) {
            thumb.validate();
        }
    }
}
