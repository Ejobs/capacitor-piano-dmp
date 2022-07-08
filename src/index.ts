import { registerPlugin } from '@capacitor/core';

import type { PianoDmpPlugin } from './definitions';

const PianoDmp = registerPlugin<PianoDmpPlugin>('PianoDmp', {
  web: () => import('./web').then(m => new m.PianoDmpWeb()),
});

export * from './definitions';
export { PianoDmp };
