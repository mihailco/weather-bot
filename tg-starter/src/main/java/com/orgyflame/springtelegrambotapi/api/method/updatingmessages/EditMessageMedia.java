package com.orgyflame.springtelegrambotapi.api.method.updatingmessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.PartialBotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.Message;
import com.orgyflame.springtelegrambotapi.api.object.media.InputMedia;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.InlineKeyboardMarkup;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Use this method to edit audio, document, photo, or video messages.
 * f a message is part of a message album, then it can be edited only to an audio for audio albums,
 * only to a document for document albums and to a photo or a video otherwise
 * When an inline message is edited, a new file can't be uploaded.
 * Use a previously uploaded file via its file_id or specify a URL.
 * On success, if the edited message was sent by the bot, the edited Message is returned, otherwise True is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditMessageMedia extends PartialBotApiMethod<Serializable> {
    public static final String PATH = "editMessageMedia";

    public static final String CHATID_FIELD = "chat_id";
    public static final String MESSAGEID_FIELD = "message_id";
    public static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    public static final String MEDIA_FIELD = "media";
    public static final String REPLYMARKUP_FIELD = "reply_markup";

    /**
     * Required if inline_message_id is not specified. Unique identifier for the chat to send the
     * message to (Or username for channels)
     */
    @JsonProperty(CHATID_FIELD)
    private String chatId;
    /**
     * Required if inline_message_id is not specified. Unique identifier of the sent message
     */
    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId;
    /**
     * Required if chat_id and message_id are not specified. Identifier of the inline message
     */
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId;
    /**
     * A JSON-serialized object for a new media content of the message
     */
    @NonNull
    @JsonProperty(MEDIA_FIELD)
    private InputMedia media;

    @JsonProperty(REPLYMARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. A JSON-serialized object for an inline keyboard.

    @Override
    public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error editing message text", result);
            }
        } catch (IOException e) {
            try {
                ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                        new TypeReference<ApiResponse<Boolean>>() {
                        });
                if (result.getOk()) {
                    return result.getResult();
                } else {
                    throw new TelegramApiRequestException("Error editing message text", result);
                }
            } catch (IOException e2) {
                throw new TelegramApiRequestException("Unable to deserialize response", e);
            }
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inlineMessageId == null) {
            if (chatId == null || chatId.isEmpty()) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (messageId == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chatId != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (messageId != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }
        if (media == null) {
            throw new TelegramApiValidationException("Media parameter can't be empty", this);
        }

        media.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
