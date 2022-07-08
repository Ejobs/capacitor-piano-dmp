export interface PianoDmpPlugin {
  /**
   * Send a pageview event. Pageview events are aggregated by Piano Insight. All collected pageview events are available 
   * for analysis in Insight's web interface.
   * @param options 
   */
  sendPageView(options: PageViewOptions): Promise<void>
}

export interface PageViewOptions {
  location: string
  userParams?: Record<string, string>
  customParams?: Record<string, string>
}
