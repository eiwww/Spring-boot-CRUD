package test.api.models;

public record AuthRequest(
    String username,
    String password
) {}
