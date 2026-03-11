package net.hytaledepot.templates.plugin.config;

public enum ConfigPluginLifecycle {
  NEW,
  PRELOADING,
  SETTING_UP,
  READY,
  RUNNING,
  STOPPING,
  STOPPED,
  FAILED
}
