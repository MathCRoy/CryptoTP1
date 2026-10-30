import tkinter as tk
from tkinter import ttk, scrolledtext
from collections import Counter
import re

class VigenereAnalyzer:
    def __init__(self, root):
        self.root = root
        self.root.title("Vigenère")
        self.root.geometry("900x750")
        self.root.configure(bg='#f0f0f0')
        
        # Variables
        self.key_var = tk.StringVar(value="key")
        self.key_size_var = tk.StringVar(value="4")
        self.x_var = tk.StringVar(value="0")
        self.y_var = tk.StringVar(value="1")
        
        self.create_widgets()
        
    def create_widgets(self):
        # Frame principal
        main_frame = ttk.Frame(self.root, padding="10")
        main_frame.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
        
        # Configuration du grid
        self.root.columnconfigure(0, weight=1)
        self.root.rowconfigure(0, weight=1)
        main_frame.columnconfigure(1, weight=1)
        
        # Message clair
        ttk.Label(main_frame, text="Message clair").grid(row=0, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,5))
        self.clear_text = scrolledtext.ScrolledText(main_frame, height=4, wrap=tk.WORD)
        self.clear_text.grid(row=1, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,10))
        
        # Message chiffré
        ttk.Label(main_frame, text="Message chiffré").grid(row=2, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,5))
        self.cipher_text = scrolledtext.ScrolledText(main_frame, height=4, wrap=tk.WORD)
        self.cipher_text.grid(row=3, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,10))
        
        # Frame pour les contrôles
        control_frame = ttk.Frame(main_frame)
        control_frame.grid(row=4, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,10))
        control_frame.columnconfigure(1, weight=1)
        
        # Key
        ttk.Label(control_frame, text="Clé:").grid(row=0, column=0, padx=(0,5))
        key_entry = ttk.Entry(control_frame, textvariable=self.key_var, width=15)
        key_entry.grid(row=0, column=1, sticky=tk.W, padx=(0,10))
        
        # Boutons Chiffrer/Déchiffrer
        ttk.Button(control_frame, text="Chiffrer", command=self.encrypt).grid(row=0, column=2, padx=5)
        ttk.Button(control_frame, text="Déchiffrer", command=self.decrypt).grid(row=0, column=3, padx=5)
        
        # Taille de la clé et IC(x)
        size_frame = ttk.Frame(main_frame)
        size_frame.grid(row=5, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,10))
        
        ttk.Label(size_frame, text="Taille de la clé:").grid(row=0, column=0, padx=(0,5))
        ttk.Entry(size_frame, textvariable=self.key_size_var, width=10).grid(row=0, column=1, padx=(0,20))
        ttk.Button(size_frame, text="IC(x)", command=self.calculate_ic).grid(row=0, column=2)
        
        # Indice de coïncidence simple
        ic_simple_frame = ttk.LabelFrame(main_frame, text="Indice de coïncidence simple : mettre votre message dans la zone du message chiffré, appuyer sur le bouton IC pour obtenir les indices\nde coïncidence des colonnes Y(0), Y(1), Y(k-1) avec Y(i) est la colonne contenant les lettres ayant les positions k*j+i avec k est la taille\nde la clé et j=0,1,2...", padding="10")
        ic_simple_frame.grid(row=6, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,10))
        ic_simple_frame.columnconfigure(3, weight=1)
        
        # Résultats IC simple
        self.ic_results = scrolledtext.ScrolledText(ic_simple_frame, height=4)
        self.ic_results.grid(row=0, column=0, columnspan=4, sticky=(tk.W, tk.E), pady=(0,10))
        
        # Contrôles IC simple
        ic_control_frame = ttk.Frame(ic_simple_frame)
        ic_control_frame.grid(row=1, column=0, columnspan=4, sticky=(tk.W, tk.E))
        
        ttk.Label(ic_control_frame, text="Taille de la clé:").grid(row=0, column=0, padx=(0,5))
        ttk.Entry(ic_control_frame, textvariable=self.key_size_var, width=5).grid(row=0, column=1, padx=(0,10))
        ttk.Label(ic_control_frame, text="x:").grid(row=0, column=2, padx=(0,5))
        ttk.Entry(ic_control_frame, textvariable=self.x_var, width=5).grid(row=0, column=3, padx=(0,10))
        ttk.Label(ic_control_frame, text="y:").grid(row=0, column=4, padx=(0,5))
        ttk.Entry(ic_control_frame, textvariable=self.y_var, width=5).grid(row=0, column=5, padx=(0,10))
        ttk.Button(ic_control_frame, text="IC(x,y-k)", command=self.calculate_ic_mutual).grid(row=0, column=6, padx=(10,0))
        
        # Indice de coïncidence mutuel
        ic_mutual_frame = ttk.LabelFrame(main_frame, text="Indice de coïncidence mutuel : mettre votre message dans la zone du message chiffré, appuyer sur le bouton IC(x,y-k)", padding="10")
        ic_mutual_frame.grid(row=7, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,10))
        ic_mutual_frame.columnconfigure(0, weight=1)
        
        # Résultats IC mutuel
        self.ic_mutual_results = scrolledtext.ScrolledText(ic_mutual_frame, height=4)
        self.ic_mutual_results.grid(row=0, column=0, columnspan=4, sticky=(tk.W, tk.E), pady=(0,10))
        
        # Analyse des fréquences
        freq_frame = ttk.Frame(main_frame)
        freq_frame.grid(row=8, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=(0,10))
        
        ttk.Label(freq_frame, text="Taille de la clé:").grid(row=0, column=0, padx=(0,5))
        ttk.Entry(freq_frame, textvariable=self.key_size_var, width=5).grid(row=0, column=1, padx=(0,20))
        ttk.Label(freq_frame, text="x:").grid(row=0, column=2, padx=(0,5))
        ttk.Entry(freq_frame, textvariable=self.x_var, width=5).grid(row=0, column=3, padx=(0,20))
        ttk.Button(freq_frame, text="Fréquences dans Y(x)", command=self.calculate_frequencies).grid(row=0, column=4)
        
        # Résultats des fréquences
        freq_results_frame = ttk.LabelFrame(main_frame, text="Fréquences des lettres de la colonne Y(x) du message chiffré ordonnées par ordre décroissant", padding="10")
        freq_results_frame.grid(row=9, column=0, columnspan=2, sticky=(tk.W, tk.E, tk.N, tk.S), pady=(0,10))
        
        self.freq_results = scrolledtext.ScrolledText(freq_results_frame, height=8)
        self.freq_results.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
        
        # Configuration du redimensionnement
        main_frame.rowconfigure(9, weight=1)
        freq_results_frame.rowconfigure(0, weight=1)
        freq_results_frame.columnconfigure(0, weight=1)
        
    def vigenere_cipher(self, text, key, decrypt=False):
        """Chiffre ou déchiffre un texte avec Vigenère"""
        if not key:
            return text
        
        result = []
        key = key.upper()
        key_length = len(key)
        key_index = 0
        
        for char in text.upper():
            if char.isalpha():
                # Calcul du décalage
                shift = ord(key[key_index % key_length]) - ord('A')
                if decrypt:
                    shift = -shift
                
                # Application du décalage
                shifted_char = chr((ord(char) - ord('A') + shift) % 26 + ord('A'))
                result.append(shifted_char)
                key_index += 1
            else:
                result.append(char)
        
        return ''.join(result)
    
    def encrypt(self):
        """Chiffre le message clair"""
        plain_text = self.clear_text.get("1.0", tk.END).strip()
        key = self.key_var.get()
        if plain_text and key:
            encrypted = self.vigenere_cipher(plain_text, key, decrypt=False)
            self.cipher_text.delete("1.0", tk.END)
            self.cipher_text.insert("1.0", encrypted)
    
    def decrypt(self):
        """Déchiffre le message chiffré"""
        cipher_text = self.cipher_text.get("1.0", tk.END).strip()
        key = self.key_var.get()
        if cipher_text and key:
            decrypted = self.vigenere_cipher(cipher_text, key, decrypt=True)
            self.clear_text.delete("1.0", tk.END)
            self.clear_text.insert("1.0", decrypted)
    
    def calculate_ic(self):
        """Calcule l'indice de coïncidence simple"""
        cipher_text = self.cipher_text.get("1.0", tk.END).strip()
        key_size = int(self.key_size_var.get())
        
        # Nettoyer le texte (garder seulement les lettres)
        clean_text = re.sub(r'[^A-Za-z]', '', cipher_text.upper())
        
        results = []
        
        # Calculer l'IC pour chaque colonne
        for i in range(key_size):
            column = ""
            for j in range(i, len(clean_text), key_size):
                column += clean_text[j]
            
            ic_value = self.calculate_ic_for_text(column)
            results.append(f"IC(Y({i}))= {round(ic_value, 6)}")
        
        self.ic_results.delete("1.0", tk.END)
        self.ic_results.insert("1.0", "\n".join(results))
    
    def calculate_ic_for_text(self, text):
        """Calcule l'indice de coïncidence pour un texte donné"""
        if len(text) <= 1:
            return 0.0
        
        n = len(text)
        freq = Counter(text)
        
        ic = sum(f * (f - 1) for f in freq.values()) / (n * (n - 1))
        return ic
    
    def calculate_ic_mutual(self):
        """Calcule l'indice de coïncidence mutuel"""
        cipher_text = self.cipher_text.get("1.0", tk.END).strip()
        key_size = int(self.key_size_var.get())
        x = int(self.x_var.get())
        y = int(self.y_var.get())
        
        # Nettoyer le texte
        clean_text = re.sub(r'[^A-Za-z]', '', cipher_text.upper())
        
        results = []
        
        # Calculer l'IC mutuel pour différentes valeurs de k
        for k in range(26):  # 0 à 25
            column_x = ""
            column_y_shifted = ""
            
            # Extraire la colonne x
            for j in range(x, len(clean_text), key_size):
                column_x += clean_text[j]
            
            # Extraire la colonne y décalée de k
            for j in range(y, len(clean_text), key_size):
                if j < len(clean_text):
                    shifted_char = chr((ord(clean_text[j]) - ord('A') - k) % 26 + ord('A'))
                    column_y_shifted += shifted_char
            
            ic_mutual = self.calculate_mutual_ic(column_x, column_y_shifted)
            results.append(f"k={k}, IC(x,y-k)={ic_mutual:.15f}")
        
        self.ic_mutual_results.delete("1.0", tk.END)
        self.ic_mutual_results.insert("1.0", "\n".join(results))
    
    def calculate_mutual_ic(self, text1, text2):
        """Calcule l'indice de coïncidence mutuel entre deux textes"""
        if len(text1) == 0 or len(text2) == 0:
            return 0.0
        
        # Compter les fréquences dans chaque texte
        freq1 = Counter(text1)
        freq2 = Counter(text2)
        
        # Calculer l'IC mutuel : Σ(fi_x * fi_y) / (|X| * |Y|)
        ic_mutual = 0.0
        for letter in 'ABCDEFGHIJKLMNOPQRSTUVWXYZ':
            f1 = freq1.get(letter, 0)
            f2 = freq2.get(letter, 0)
            ic_mutual += f1 * f2
        
        return ic_mutual / (len(text1) * len(text2))
    
    def calculate_frequencies(self):
        """Calcule les fréquences des lettres dans la colonne Y(x)"""
        cipher_text = self.cipher_text.get("1.0", tk.END).strip()
        key_size = int(self.key_size_var.get())
        x = int(self.x_var.get())
        
        # Nettoyer le texte
        clean_text = re.sub(r'[^A-Za-z]', '', cipher_text.upper())
        
        # Extraire la colonne x
        column = ""
        for j in range(x, len(clean_text), key_size):
            column += clean_text[j]
        
        # Compter les fréquences
        freq = Counter(column)
        
        # Trier par fréquence décroissante
        sorted_freq = sorted(freq.items(), key=lambda x: x[1], reverse=True)
        
        # Afficher les résultats
        results = []
        for letter, count in sorted_freq:
            if count > 0:
                results.append(f"Fréquence de {letter.lower()}={count}")
        
        # Ajouter les lettres avec fréquence 0
        all_letters = set('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
        present_letters = set(freq.keys())
        absent_letters = all_letters - present_letters
        
        for letter in sorted(absent_letters):
            results.append(f"Fréquence de {letter.lower()}=0")
        
        self.freq_results.delete("1.0", tk.END)
        self.freq_results.insert("1.0", "\n".join(results))

if __name__ == "__main__":
    root = tk.Tk()
    app = VigenereAnalyzer(root)
    root.mainloop()
