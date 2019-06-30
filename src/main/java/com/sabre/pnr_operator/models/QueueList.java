package com.sabre.pnr_operator.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class QueueList {

    private List<QueueLine> queueLineList;
    private List<String> queueListParagraph;
}
