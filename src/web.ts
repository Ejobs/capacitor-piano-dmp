import { WebPlugin } from '@capacitor/core';

import type { PageViewOptions, PianoDmpPlugin } from './definitions';

export class PianoDmpWeb extends WebPlugin implements PianoDmpPlugin {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  async sendPageView(_options: PageViewOptions): Promise<void> {
    throw new Error('Not implemented on web')
  }
}
