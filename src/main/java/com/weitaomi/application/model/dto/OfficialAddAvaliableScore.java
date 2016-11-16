package com.weitaomi.application.model.dto;

/**
 * Created by Administrator on 2016/11/14.
 */
public class OfficialAddAvaliableScore {
    private Long id;
    private Long memberId;
    private Double score;

    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Double getScore() {
        return this.score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
