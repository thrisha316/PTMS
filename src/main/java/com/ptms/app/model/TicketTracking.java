package com.ptms.app.model;

import java.time.LocalDateTime;

public class TicketTracking {

    private int id;
    private int ticketId;
    private int updatedBy;
    private String status;
    private int progress;
    private String comment;
    private LocalDateTime updatedAt;

    // No-argument constructor
    public TicketTracking() {
    }

    public TicketTracking(int ticketId, int updatedBy, String status,int progress, String comment) {

        this.ticketId = ticketId;
        this.updatedBy = updatedBy;
        this.status = status;
        this.progress = progress;
        this.comment = comment;
    }

    public TicketTracking(int id, int ticketId, int updatedBy, String status, int progress, String comment, LocalDateTime updatedAt) {

        this.id = id;
        this.ticketId = ticketId;
        this.updatedBy = updatedBy;
        this.status = status;
        this.progress = progress;
        this.comment = comment;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TicketTracking{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", updatedBy=" + updatedBy +
                ", status='" + status + '\'' +
                ", progress=" + progress +
                ", comment='" + comment + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}