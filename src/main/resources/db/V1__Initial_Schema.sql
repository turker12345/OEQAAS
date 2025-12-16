
DROP TABLE IF EXISTS Sonuclar;
DROP TABLE IF EXISTS Kullanicilar;


CREATE TABLE Kullanicilar (
    KullaniciID INT PRIMARY KEY IDENTITY(1,1),
    AdSoyad NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100),
    Sifre NVARCHAR(50) NOT NULL,
    Telefon NVARCHAR(20),
    Rol NVARCHAR(20) DEFAULT 'OGRENCI'
);


CREATE TABLE Sonuclar (
    SonucID INT PRIMARY KEY IDENTITY(1,1),
    KullaniciID INT NOT NULL,
    TestAdi NVARCHAR(100), 
    DogruSayisi INT DEFAULT 0,
    YanlisSayisi INT DEFAULT 0,
    Tarih DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (KullaniciID) REFERENCES Kullanicilar(KullaniciID) ON DELETE CASCADE
);

INSERT INTO Kullanicilar (AdSoyad, Email, Sifre, Telefon, Rol)
VALUES ('Admin', 'admin@email.com', '123', '555-0000', 'ADMIN');