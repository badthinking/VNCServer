package com.jcope.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class PasswordInputDialog
{
    private static int passwordPixelWidth = 200;
    
    public static String show(JFrame parent, String title, String prompt, boolean confirm, Integer capacity)
    {
        char[] tmp = null;
        char[] confirmTmp = null;
        JPanel panel = new JPanel();
        JPanel passPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(prompt);
        final JPasswordField pass = capacity == null ? new JPasswordField() : new JPasswordField(capacity);
        passPanel.add(pass, confirm ? BorderLayout.NORTH : BorderLayout.CENTER);
        
        pass.setPreferredSize(new Dimension(passwordPixelWidth, pass.getPreferredSize().height));
        JPasswordField confirmPass = null;
        if (confirm)
        {
            confirmPass = capacity == null ? new JPasswordField() : new JPasswordField(capacity);
            confirmPass.setPreferredSize(new Dimension(passwordPixelWidth, pass.getPreferredSize().height));
        }
        panel.add(label, BorderLayout.WEST);
        if (confirm)
        {
            passPanel.add(confirmPass, BorderLayout.SOUTH);
        }
        panel.add(passPanel, BorderLayout.EAST);
        
        AncestorListener al = new AncestorListener(){

            @Override
            public void ancestorAdded(AncestorEvent arg0)
            {
                pass.requestFocusInWindow();
            }

            @Override
            public void ancestorMoved(AncestorEvent arg0)
            {
                // Do Nothing
            }

            @Override
            public void ancestorRemoved(AncestorEvent arg0)
            {
                // Do Nothing
            }
            
        };
        
        String[] options = new String[]{"OK", "Cancel"};
        panel.addAncestorListener(al);
        int resultOption;
        boolean done = false;
        
        try
        {
            do
            {
                pass.setText("");
                if (confirm)
                {
                    confirmPass.setText("");
                }
                resultOption = JOptionPane.showOptionDialog(parent, panel, title,
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                    options[0]);
                if (JOptionPane.OK_OPTION == resultOption)
                {
                    if ((tmp = pass.getPassword()).length > 0 && (!confirm || ((confirmTmp = confirmPass.getPassword()).length > 0 && tmp.length == confirmTmp.length && Arrays.equals(tmp, confirmTmp))))
                    {
                        return new String(tmp);
                    }
                    if (confirm)
                    {
                        resultOption = JOptionPane.showConfirmDialog(parent, (tmp == null || tmp.length <= 0 || confirmTmp == null || confirmTmp.length <= 0) ? "Password fields not filled.\nTry again?" : "Passwords do not match.\nTry again?");
                        if (JOptionPane.OK_OPTION == resultOption)
                        {
                            continue;
                        }
                    }
                }
                done = true;
            } while (!done);
        }
        finally {
            try
            {
                try
                {
                    pass.setText("");
                }
                finally {
                    if (confirm)
                    {
                        confirmPass.setText("");
                    }
                }
            }
            finally {
                panel.removeAncestorListener(al);
            }
        }
        
        return null;
    }
}