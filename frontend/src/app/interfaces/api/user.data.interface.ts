export interface Iuser{
    name: string,
    profile?: string,
    mail: string,
    role: "USER"|"VERIFY_MAIL"|"ADMIN"|"BANNED",
    resume?: string,
    githubURL?: string,
    instagramURL?: string,
    portfolioURL?: string
  }