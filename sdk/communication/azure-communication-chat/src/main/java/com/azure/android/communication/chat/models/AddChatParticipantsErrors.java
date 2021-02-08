// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.android.communication.chat.models;

import com.azure.android.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The AddChatParticipantsErrors model.
 */
@Fluent
public final class AddChatParticipantsErrors {
    /*
     * The participants that failed to be added to the chat thread.
     */
    @JsonProperty(value = "invalidParticipants", required = true)
    private List<CommunicationError> invalidParticipants;

    /**
     * Get the invalidParticipants property: The participants that failed to be
     * added to the chat thread.
     * 
     * @return the invalidParticipants value.
     */
    public List<CommunicationError> getInvalidParticipants() {
        return this.invalidParticipants;
    }

    /**
     * Set the invalidParticipants property: The participants that failed to be
     * added to the chat thread.
     * 
     * @param invalidParticipants the invalidParticipants value to set.
     * @return the AddChatParticipantsErrors object itself.
     */
    public AddChatParticipantsErrors setInvalidParticipants(List<CommunicationError> invalidParticipants) {
        this.invalidParticipants = invalidParticipants;
        return this;
    }
}