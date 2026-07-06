import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "BoardHub",
  description: "Community board frontend for minirestapi",
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="ko">
      <body>{children}</body>
    </html>
  );
}
