package lexicalAnalyzer;

public interface Opcode {
    public static final int
            FIRST          = 0, // First opcode

    // inherent opcodes:
    INHERENT_BEGIN = 0,
            halt =0x00, pop   =0x01, dup =0x02, exit=0x03, ret  =0x04, RFU1 =0x05, RFU2   =0x06, RFU3 =0x07,
            RFU4 =0x08, RFU5  =0x09, RFU6=0x0A, RFU7=0x0B, not  =0x0C, and  =0x0D,  or    =0x0E, xor  =0x0F,
            neg  =0x10, inc   =0x11, dec =0x12, add =0x13, sub  =0x14, mul  =0x15, div    =0x16, rem  =0x17,
            shl  =0x18, shr   =0x19, teq =0x1A, tne =0x1B, tlt  =0x1C, tgt  =0x1D, tle    =0x1E, tge  =0x1F,
            INHERENT_END = 0x1F,

    IMMEDIATE_BEGIN = 0x30,
            br_i5 =0x30,
            brf_i5 =0x50,
            enter_u5 =0x70,
            ldc_i3 =0x90,
            addv_u3 =0x98,
            ldv_u3 =0xA0,
            stv_u3 =0xA8,
            IMMEDIATE_END = 0xAF,

    RELATIVE_BEGIN = 0xB0,
            addv_u8 = 0xB0, ldv_u8 = 0xB1, stv_u8 = 0xB2, incv  = 0xB3, decv  = 0xB4,
            enter_u8 = 0xBF,

    lda_i16 = 0xD5,
            ldc_i8 = 0xD9, ldc_i16 = 0xDA, ldc_i32 = 0xDB,

    br_i8 = 0xE0, br_i16  = 0xE1,
            brf_i8 = 0xE3,

    calls_i8 = 0xE6, calls_i16 = 0xE7,

    br   = br_i16,    // default
            brf   = brf_i8,    // default

    trap = 0xFF,

    RELATIVE_END = 0xFF,
            LAST         = 0xFF, // Last opcode

    //========================== Directives :

    _CSTRING = 0x100,

    _INVALID_TOKEN = 0x200;

    public static final int inherentOpcodes[] = {
            halt,  pop,    dup,  exit,  ret,   RFU1,  RFU2,    RFU3,
            RFU4,  RFU5,   RFU6, RFU7,  not,   and,   or,      xor,
            neg,   inc,    dec,  add,   sub,   mul,   div,     rem,
            shl,   shr,    teq,  tne,   tlt,   tgt,   tle,     tge
    };
    public static final String inherentMnemonics[] = {
            "halt",  "pop",    "dup",  "exit",  "ret",   "rfu1",  "rfu2",    "rfu3",
            "rfu4",  "rfu5",   "rfu6", "rfu7",  "not",   "and",   "or",      "xor",
            "neg",   "inc",    "dec",  "add",   "sub",   "mul",   "div",     "rem",
            "shl",   "shr",    "teq",  "tne",   "tlt",   "tgt",   "tle",     "tge"
    };
    public static final String immediateMnemonics[] = {
            "br.i5", "brf.i5", "enter.u5", "ldc.i3", "addv.u3", "ldv.u3", "stv.u3"
    };

    public static final int immediateOpcodes[] = {
            br_i5, brf_i5, enter_u5, ldc_i3, addv_u3, ldv_u3, stv_u3
    };
}