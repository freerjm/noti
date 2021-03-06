package application;

import java.util.UUID;

public class Target {

  private final UUID uuid;
  private final String name;
  private final String phoneNumber;

  public Target() {
    this(null, null, null);
  }

  public Target(final String name, final String phoneNumber) {
    this(null, name, phoneNumber);
  }

  public Target(final UUID uuid, final String name, final String phoneNumber) {
    this.uuid = uuid;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public UUID getUUID() {
    return this.uuid;
  }

  public String getName() {
    return this.name;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }
}
