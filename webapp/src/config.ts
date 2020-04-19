
export const IS_DEV: boolean = process.env.NODE_ENV === "development";
export const APP_URL = IS_DEV ? "http://localhost:8081/rest/v1/" : "/rest/v1/";