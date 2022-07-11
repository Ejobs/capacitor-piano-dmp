/// <reference types="@capacitor/cli" />
export interface PianoDmpPlugin {
  /**
   * Send a pageview event. Pageview events are aggregated by Piano Insight. All collected pageview events are available
   * for analysis in Insight's web interface.
   * @param options
   */
  sendPageView(options: PageViewOptions): Promise<void>;
}

export interface PageViewOptions {
  location: string;
  userParams?: Record<string, string>;
  customParams?: Record<string, string>;
}

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    PianoDmp?: {
      /**
       * Identifier of the site for which current event will be reported
       *
       * @since 1.0.0
       * @example "12345678910"
       */
      siteId?: string;
      /**
       * Piano API username
       *
       * @since 1.0.0
       * @example "username@email.com"
       */
      username?: string;
      /**
       * Piano API apiKey
       *
       * @since 1.0.0
       * @example "KffnjdfUB23309fnndf"
       */
      apiKey?: string;
    };
  }
}
