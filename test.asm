; Sample program
            ldc.i3      0
            dup
            stv.u3      1            ; n = 0
            stv.u3      1            ; sum = 0
Loop        ldv.u3      0            ; push n
            ldc.i3      10           ; push 10
            tlt                      ; if n < 10 then Continue
            brf.i5 Done              ; else Done
Continue    ldv.u3      1            ; push sum
            ldv.u3      0            ; push n
            add                      ; add n to sum
            stv.u3      1            ; store sum
            br.i5 Loop
Done
            halt